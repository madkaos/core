package com.madkaos.core.player;

import java.util.ArrayList;
import java.util.List;

import com.dotphin.milkshakeorm.utils.MapFactory;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.commands.CommandExecutor;
import com.madkaos.core.player.entities.PlayerData;
import com.madkaos.core.player.entities.PlayerPunishment;
import com.madkaos.core.player.entities.PlayerSettings;
import com.madkaos.core.utils.ProxyUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class MadPlayer extends CommandExecutor {
    protected MadKaosCore plugin;
    protected Player bukkitPlayer;

    protected PlayerData data = null;
    protected PlayerSettings setttings = null;

    private boolean vanished = false;
    private long lastMessage = 0;
    private long lastCommand = 0;
    
    public MadPlayer(MadKaosCore plugin, Player bukkitPlayer) {
        super(plugin, bukkitPlayer);
        
        this.plugin = plugin;
        this.bukkitPlayer = bukkitPlayer;
    }

    public void setLastMessage() {
        this.lastMessage = System.currentTimeMillis();
    }

    public void setLastCommand() {
        this.lastCommand = System.currentTimeMillis();
    }

    public long getLastMessage() {
        return System.currentTimeMillis() - this.lastMessage;
    }

    public long getLastCommand() {
        return System.currentTimeMillis() - this.lastCommand;
    }

    public Player getBukkitPlayer() {
        return this.bukkitPlayer;
    }

    public PlayerData getData() {
        return this.data;
    }

    public PlayerSettings getSettings() {
        return this.setttings;
    }

    public String getAddress() {
        return this.bukkitPlayer.getAddress().getAddress().toString();
    }

    public boolean isFriend(String id) {
        return this.data.friends.contains(id);
    }

    public boolean isFriend(MadPlayer player) {
        return this.isFriend(player.getData().id);
    }

    public void sendToServer(String server) {
        ProxyUtils.sendPlayerToServer(this, server);
    }

    public void sendPluginMessage(String channel, byte[] message) {
        this.getBukkitPlayer().sendPluginMessage(this.plugin, channel, message);
    }

    public PlayerPunishment[] getPunishments() {
        return this.plugin.getPlayerPunishmentsRepository().findMany(
            MapFactory.create("playerId", this.getData().id)
        );
    }

    public PlayerPunishment[] getPunishmentsEmited() {
        return this.plugin.getPlayerPunishmentsRepository().findMany(
            MapFactory.create("emisorId", this.getData().id)
        );
    }

    public PlayerData[] getAlts() {
        return this.plugin.getPlayerDataRepository().findMany(
            MapFactory.create("address", this.getAddress())
        );
    }

    /* Utils */
    public List<PlayerData> getFriends() {
        List<PlayerData> friends = new ArrayList<>();

        for (String id : this.data.friends) {
            friends.add(this.plugin.getPlayerDataRepository().findByID(id));
        }

        return friends;
    }

    public List<PlayerData> getFriendRequests() {
        List<PlayerData> friends = new ArrayList<>();

        for (String id : this.data.friendRequests) {
            friends.add(this.plugin.getPlayerDataRepository().findByID(id));
        }

        return friends;
    }

    public void setFlying(boolean flying) {
        bukkitPlayer.setAllowFlight(flying);
        bukkitPlayer.setFlying(flying);
    }

    public boolean isOnline() {
        return this.bukkitPlayer != null && this.bukkitPlayer.isOnline();
    }

    public boolean isVanished() {
        return this.vanished;
    }

    public void setVanish(boolean vanished) {
        if (this.vanished == vanished) {
            return;
        } else {
            this.vanished = vanished;
        }
        
        if (vanished) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.hidePlayer(this.plugin, this.getBukkitPlayer());
            }
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.showPlayer(this.plugin, this.getBukkitPlayer());
            }
        }
    }

    public void teleport(int x, int y, int z) {
        Location loc = this.bukkitPlayer.getLocation().clone();
        loc.setX(x);
        loc.setY(y);
        loc.setZ(z);
        this.bukkitPlayer.teleport(loc);
    }

    /* Override methods */
    @Override
    public String formatMessage(String message) {
        return super.formatMessage(
            PlaceholderAPI.setPlaceholders(this.bukkitPlayer, message)
                .replace("{pron}", this.data.pron)
        );
    }

    /* Initial methods */
    public void setupPlayer() {
        // Fly
        if (this.setttings.fly) {
            this.setFlying(true);
        }

        // Vanish
        if (this.setttings.vanished) {
            this.sendI18nMessage("vanish.join");
            this.setVanish(true);
        }
    }

    public void downloadSettings() {
        if (this.data == null) {
            return;
        }

        String id = this.data.id;
        this.setttings = this.plugin.getPlayerSettingsRepository().findOne(MapFactory.create("playerId", id));

        if (this.setttings == null) {
            this.setttings = new PlayerSettings();
            this.setttings.playerId = id;
            this.setttings.save();
        }
    }

    public void downloadData() {
        String uuid = this.bukkitPlayer.getUniqueId().toString();
        String name = this.bukkitPlayer.getName();

        this.data = this.plugin.getPlayerDataRepository().findOne(MapFactory.create("uuid", uuid));

        if (this.data == null) {
            this.data = new PlayerData();
            this.data.uuid = uuid;
            this.data.username = name.toLowerCase();
            this.data.displayName = name;
            this.data.save();
        } else if (!this.data.username.equalsIgnoreCase(name)) {
            this.data.username = name.toLowerCase();
            this.data.displayName = name;
            this.data.save();
        }
    }
}
