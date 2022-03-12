package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private MadKaosCore plugin;
    
    public PlayerJoinListener(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    private void handleMotd(MadPlayer player) {
        player.sendMessage(this.plugin.getMainConfig().getString("motd"));
    }

    private String handleJoinMessage(MadPlayer player) {
        if (player.getBukkitPlayer().hasPermission("core.module.join-message") && !player.getSettings().vanished) {
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
        MadPlayer player = this.plugin.getPlayerManager().addPlayer(e.getPlayer());
        player.setupPlayer();

        this.handleMotd(player);
        this.handleVanish(player);
        e.setJoinMessage(handleJoinMessage(player));
    }
}
