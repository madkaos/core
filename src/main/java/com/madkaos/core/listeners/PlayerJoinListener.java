package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.config.Configuration;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private MadKaosCore plugin;
    private Configuration config;
    
    public PlayerJoinListener(MadKaosCore plugin) {
        this.plugin = plugin;
        this.config = plugin.getMainConfig();
    }

    private void handleSpawnTP(MadPlayer player) {
        if (this.config.getBoolean("spawn.enabled") && this.config.getBoolean("spawn.teleport-on-join")) {
            Location loc = config.getLocation("spawn.position", true);
            player.getBukkitPlayer().teleport(loc);
        }
    }

    private void handleMotd(MadPlayer player) {
        if (this.config.getBoolean("join.motd.enabled")) {
            player.sendMessage(this.config.getString("join.motd.message"));
        }
    }

    private void handleTitle(MadPlayer player) {
        if (this.config.getBoolean("join.title.enabled")) {
            player.sendTitle(
                this.config.getString("join.title.title"),
                this.config.getString("join.title.subtitle"),
                this.config.getInt("join.title.duration")
            );
        }
    }

    private void handleSound(MadPlayer player) {
        if (this.config.getBoolean("join.sound.enabled")) {
            player.playSound(this.config.getSound("join.sound.sound", "LEVELUP"));
        }
    }

    private String handleJoinMessage(MadPlayer player) {
        if (player.getBukkitPlayer().hasPermission("core.join-message") && !player.isVanished() && this.plugin.isLobby()) {
            return player.formatMessage(player.getI18nMessage("join-message"));
        } else {
            return null;
        }
    }

    private void handleVanish(MadPlayer player) {
        for (MadPlayer mp : this.plugin.getPlayerManager().getPlayers()) {
            if (mp.isVanished()) {
                player.getBukkitPlayer().hidePlayer(this.plugin, mp.getBukkitPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        MadPlayer player = this.plugin.getPlayerManager().getPlayer(e.getPlayer());
        
        String currentAddress = player.getAddress();
        
        if (player.getData().address == null || player.getData().address != currentAddress) {
            player.getData().address = currentAddress;
            player.getData().save();
        }

        player.setupPlayer();

        this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
            this.handleTitle(player);
            this.handleSound(player);
        }, 40L);

        this.handleMotd(player);
        this.handleVanish(player);
        this.handleSpawnTP(player);

        e.setJoinMessage(handleJoinMessage(player));
    }
}
