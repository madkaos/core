package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    private MadKaosCore plugin;
    
    public PlayerMoveListener(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getFrom().distance(e.getTo()) >= 0.1) {
            MadPlayer player = this.plugin.getPlayerManager().getPlayer(e.getPlayer());
            if (player.getPendingTeleport() != null) {
                player.cancelPendingTeleport();
                player.sendI18nMessage("teleport.teleport-cancelled");
            }
        }
    }
}
