package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private MadKaosCore plugin;
    
    public PlayerQuitListener(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        this.plugin.getPlayerManager().removePlayer(e.getPlayer());
    }
}
