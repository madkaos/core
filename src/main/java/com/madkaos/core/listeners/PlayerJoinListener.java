package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private MadKaosCore plugin;
    
    public PlayerJoinListener(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    private void handleSpawnTP(MadPlayer player) {
        if (plugin.getMainConfig().getBoolean("spawn.teleport-on-join")) {
            Location loc = plugin.getMainConfig().getLocation("spawn.position", true);
            player.getBukkitPlayer().teleport(loc);
        }
    }

    private void handleMotd(MadPlayer player) {
        player.sendMessage(this.plugin.getMainConfig().getString("motd"));
    }

    private String handleJoinMessage(MadPlayer player) {
        if (player.getBukkitPlayer().hasPermission("core.join-message") && this.plugin.isLobby()) {
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

        this.handleMotd(player);
        this.handleVanish(player);
        this.handleSpawnTP(player);

        e.setJoinMessage(handleJoinMessage(player));
        player.setupPlayer();
    }
}
