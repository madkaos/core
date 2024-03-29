package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;

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
        MadPlayer player = this.plugin.getPlayerManager().getPlayer(e.getPlayer());
        player.free();
        
        e.setQuitMessage(null);
    }
}
