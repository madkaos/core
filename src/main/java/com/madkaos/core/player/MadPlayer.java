package com.madkaos.core.player;

import java.util.ArrayList;
import java.util.List;

import com.dotphin.milkshakeorm.utils.MapFactory;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.commands.CommandExecutor;
import com.madkaos.core.player.entities.PlayerData;
import com.madkaos.core.player.entities.PlayerSettings;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MadPlayer extends CommandExecutor {
    protected MadKaosCore plugin;
    protected Player bukkitPlayer;

    protected PlayerData data = null;
    protected PlayerSettings setttings = null;

    private boolean vanished = false;
    
    public MadPlayer(MadKaosCore plugin, Player bukkitPlayer) {
        super(plugin, bukkitPlayer);
        
        this.plugin = plugin;
        this.bukkitPlayer = bukkitPlayer;
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

    public List<PlayerData> getFriends() {
        String[] friendIds = this.data.friends;
        List<PlayerData> friends = new ArrayList<>();

        for (String id : friendIds) {
            friends.add(this.plugin.getPlayerDataRepository().findByID(id));
        }

        return friends;
    }

    /* Utils */
    public void setFlying(boolean flying) {
        bukkitPlayer.setAllowFlight(flying);
        bukkitPlayer.setFlying(flying);
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

    /* Initial methods */
    void setupPlayer() {
        this.downloadData();
        this.downloadSettings();

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

    void downloadSettings() {
        String id = this.data.id;

        this.setttings = this.plugin.getPlayerSettingsRepository().findOne(MapFactory.create("playerId", id));

        if (this.setttings == null) {
            this.setttings = new PlayerSettings();
            this.setttings.playerId = id;
            this.setttings.save();
        }
    }

    void downloadData() {
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
