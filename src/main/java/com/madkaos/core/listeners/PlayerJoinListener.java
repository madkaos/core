package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private MadKaosCore plugin;
    
    public PlayerJoinListener(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        this.plugin.getPlayerManager().addPlayer(e.getPlayer());
    }
}
