package com.madkaos.core.player;

import java.util.ArrayList;
import java.util.List;

import com.dotphin.milkshakeorm.utils.MapFactory;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.commands.CommandExecutor;
import com.madkaos.core.messaging.packets.PlayerPunishPacket;
import com.madkaos.core.player.entities.PlayerData;
import com.madkaos.core.player.entities.PlayerHome;
import com.madkaos.core.player.entities.PlayerPunishment;
import com.madkaos.core.player.entities.PlayerSettings;
import com.madkaos.core.utils.ProxyUtils;
import com.madkaos.core.utils.PunishmentsUtil;
import com.madkaos.core.utils.ServerUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class MadPlayer extends CommandExecutor {
    protected MadKaosCore plugin;
    protected Player bukkitPlayer;

    protected PlayerData data = null;
    protected PlayerSettings setttings = null;
    protected PlayerPunishment[] punishments = null;
    protected List<PlayerHome> homes = null;

    private PendingTeleport pendingTeleport;
    private boolean vanished = false;
    private long lastMessage = 0;
    private long lastCommand = 0;
    
    public MadPlayer(MadKaosCore plugin, Player bukkitPlayer) {
        super(plugin, bukkitPlayer);
        
        this.plugin = plugin;
        this.bukkitPlayer = bukkitPlayer;
    }

    public void free() {
        this.plugin.getPlayerManager().removePlayer(this.getBukkitPlayer());

        this.plugin = null;
        this.bukkitPlayer = null;
        this.data = null;
        this.setttings = null;
        this.punishments = null;
    }

    public void cancelPendingTeleport() {
        if (this.pendingTeleport != null) {
            this.pendingTeleport.cancel();
            this.pendingTeleport = null;
        }
    }

    public void delayedTeleport(Location location, String message) {
        this.cancelPendingTeleport();
        this.pendingTeleport = new PendingTeleport(this, location, this.plugin.getMainConfig().getInt("teleport.sound"), message);
    }

    public void teleport(Location location) {
        this.bukkitPlayer.teleport(location);
        this.playSound(this.plugin.getMainConfig().getSound("teleport.sound", "ENDERMAN_TELEPORT"));
    }

    public PendingTeleport getPendingTeleport() {
        return this.pendingTeleport;
    }
    
    @SuppressWarnings("deprecation")
    public void sendTitle(final String title, final String subtitle, final int fadeInTime, final int showTime,
            final int fadeOutTime) {
        if (ServerUtils.isLegacy()) {
            this.getBukkitPlayer().resetTitle();
            this.getBukkitPlayer().sendTitle(this.formatMessage(title), this.formatMessage(subtitle));
        } else {
            this.getBukkitPlayer().sendTitle(
                this.formatMessage(title), 
                this.formatMessage(subtitle), 
                fadeInTime, 
                showTime, 
                fadeOutTime
            );
        }
    }
    
    public void sendTitle(final String title, final String subtitle, final int duration) {
        this.sendTitle(title, subtitle, 2, duration * 20, 2);
    }

    public void playSound(final Sound sound) {
        if (sound != null) {
            this.getBukkitPlayer().playSound(this.getBukkitPlayer().getLocation(), sound, 1, 1);
        }
    }

    public PlayerHome deleteHome(String name) {
        PlayerHome home = this.getHome(name);

        if (home != null) {
            home.delete();
            this.homes.remove(home);
        }

        return home;
    }

    public String getHomeAsString() {
        String output = "";

        for (PlayerHome home : this.getHomes()) {
            if (output != "") {
                output += ", ";
            }

            output += home.name;
        }

        if (output == "") {
            output = "(Ninguno)";
        }

        return output;
    }

    public PlayerHome createHome(String name) {
        if (this.getHome(name) == null) {
            PlayerHome home = new PlayerHome();
            home.uuid = this.getUUID();
            home.name = name;
            
            Location loc = this.getBukkitPlayer().getLocation();
            home.x = loc.getX();
            home.y = loc.getY();
            home.z = loc.getZ();
            home.pitch = loc.getPitch();
            home.yaw = loc.getYaw();
            home.world = loc.getWorld().getName();
            home.server = this.plugin.getServerID();
            home.save();

            this.homes.add(home);
            return home;
        } else {
            return null;
        }
    }

    public boolean hasPermission(String perm) {
        return this.bukkitPlayer.hasPermission(perm);
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
        return this.isFriend(player.getUUID());
    }

    public void sendToServer(String server) {
        ProxyUtils.sendPlayerToServer(this, server);
    }

    public void sendPluginMessage(String channel, byte[] message) {
        this.getBukkitPlayer().sendPluginMessage(this.plugin, channel, message);
    }

    public void kick(String message) {
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            this.getBukkitPlayer().kickPlayer(message);
        }, 1L);
    }

    public void unban(MadPlayer emisor) {
        PlayerPunishment prevBan = this.getActiveBan();

        if (prevBan != null) {
            if (emisor == null) {
                prevBan.revokedId = "console";
            } else {
                prevBan.revokedId = emisor.getUUID();
            }

            prevBan.save();
        }
    }

    public void unban() {
        this.unban(null);
    }

    public void ban(MadPlayer emisor, String reason, int time) {
        String emisorId = "console";
        String emisorName = "Consola";

        if (emisor != null) {
            emisorId = emisor.getUUID();
            emisorName = emisor.getData().displayName;
        }

        PlayerPunishment punishment = new PlayerPunishment();
        punishment.createdOn = System.currentTimeMillis();
        punishment.expiresOn = time;
        punishment.emisorId = emisorId;
        punishment.ofIP = false;
        punishment.uuid = this.getUUID();
        punishment.reason = reason;
        punishment.type = PunishmentType.BAN;
        punishment.save();

        this.plugin.getMessageBroker().publish(
            new PlayerPunishPacket(
                emisorName,
                this.getData().displayName,
                punishment
            )
        );
    }

    public void ban(String reason, int time) {
        this.ban(null, reason, time);
    }

    public void ban(String reason) {
        this.ban(reason, -1);
    }

    public PlayerPunishment[] getPunishments() {
        return this.punishments;
    }

    public PlayerPunishment[] getPunishmentsEmited() {
        return this.plugin.getPlayerPunishmentsRepository().findMany(
            MapFactory.create("uuid", this.getUUID())
        );
    }

    public PlayerPunishment getActivePunishment(int type) {
        for (PlayerPunishment punishment : this.getPunishments()) {
            if (punishment.type == type && PunishmentsUtil.isActive(punishment)) {
                return punishment;
            }
        }
    
        return null;
    }

    public PlayerPunishment getActiveBan() {
        return this.getActivePunishment(PunishmentType.BAN);
    }

    public PlayerPunishment getActiveMute() {
        return this.getActivePunishment(PunishmentType.MUTE);
    }

    public PlayerData[] getAlts() {
        return this.plugin.getPlayerDataRepository().findMany(
            MapFactory.create("address", this.getAddress())
        );
    }

    /* Utils */
    public String getUUID() {
        return this.bukkitPlayer.getUniqueId().toString();
    }

    public List<PlayerHome> getHomes() {
        return this.homes;
    }

    public PlayerHome getHome(String name) {
        for (PlayerHome home : this.getHomes()) {
            if (home.name.equalsIgnoreCase(name)) {
                return home;
            }
        }

        return null;
    }

    public List<PlayerData> getFriends() {
        List<PlayerData> friends = new ArrayList<>();

        for (String id : this.data.friends) {
            friends.add(this.plugin.getPlayerDataRepository().findOne(MapFactory.create("uuid", id)));
        }

        return friends;
    }

    public List<PlayerData> getFriendRequests() {
        List<PlayerData> friends = new ArrayList<>();

        for (String id : this.data.friendRequests) {
            friends.add(this.plugin.getPlayerDataRepository().findOne(MapFactory.create("uuid", id)));
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

    @SuppressWarnings("deprecation")
    public void setVanish(boolean vanished) {
        if (this.vanished == vanished) {
            return;
        } else {
            this.vanished = vanished;
        }
        
        if (vanished) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (ServerUtils.isLegacy()) {
                    player.hidePlayer(this.getBukkitPlayer());
                } else {
                    player.hidePlayer(this.plugin, this.getBukkitPlayer());
                }
            }
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (ServerUtils.isLegacy()) {
                    player.showPlayer(this.getBukkitPlayer());
                } else {
                    player.showPlayer(this.plugin, this.getBukkitPlayer());
                }
            }
        }
    }

    public void teleport(float x, float y, float z, float yaw, float pitch) {
        Location loc = this.bukkitPlayer.getLocation().clone();
        loc.setX(x);
        loc.setY(y);
        loc.setZ(z);
        loc.setYaw(yaw);
        loc.setPitch(pitch);
        this.bukkitPlayer.teleport(loc);
    }

    public void teleport(float x, float y, float z) {
        Location loc = this.bukkitPlayer.getLocation().clone();
        loc.setX(x);
        loc.setY(y);
        loc.setZ(z);
        this.bukkitPlayer.teleport(loc);
    }

    /* Override methods */
    @Override
    public String formatMessage(String message) {
        String out = PlaceholderAPI.setPlaceholders(this.bukkitPlayer, message);

        if (this.data != null) {
            out = out
                .replace("{pron}", this.data.pron);
        }

        return super.formatMessage(out);
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

    public void downloadHomes() {
        this.homes = new ArrayList<>();
        PlayerHome[] homeArray = this.plugin.getPlayerHomeRepository().findMany(
            MapFactory.create("uuid", this.getUUID()).add("server", this.plugin.getServerID())
        );
        for (PlayerHome home : homeArray) {
            this.homes.add(home);
        }
    }

    public void downloadPunishments() {
        this.punishments = this.plugin.getPlayerPunishmentsRepository().findMany(
            MapFactory.create("uuid", this.getUUID())
        );

        for (PlayerPunishment punishment : this.punishments) {
            if (punishment.emisorId == null || punishment.emisorId.equalsIgnoreCase("console")) {
                punishment.emisorName = "Consola";
            } else {
                punishment.emisorName = this.plugin.getPlayerDataRepository().findOne(
                    MapFactory.create("uuid", punishment.emisorId)
                ).displayName;
            }
        }
    }

    public void downloadSettings() {
        String uuid = this.getUUID();
        this.setttings = this.plugin.getPlayerSettingsRepository().findOne(MapFactory.create("uuid", uuid));

        if (this.setttings == null) {
            this.setttings = new PlayerSettings();
            this.setttings.uuid = uuid;
            this.setttings.save();
        }
    }

    public void downloadData() {
        String uuid = this.getUUID();
        String name = this.bukkitPlayer.getName();

        this.data = this.plugin.getPlayerDataRepository().findOne(MapFactory.create("uuid", uuid));

        if (this.data == null) {
            this.data = new PlayerData();
            this.data.uuid = uuid;
            this.data.username = name.toLowerCase();
            this.data.displayName = name;
            this.data.save();
        } else if (!this.data.username.equals(name)) {
            this.data.username = name.toLowerCase();
            this.data.displayName = name;
            this.data.save();
        }
    }

    public void download() {
        this.downloadData();
        this.downloadHomes();
        this.downloadPunishments();
        this.downloadSettings();
    }
}
