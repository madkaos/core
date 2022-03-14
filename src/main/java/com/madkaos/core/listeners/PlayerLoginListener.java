package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerLoginListener implements Listener {
    private MadKaosCore plugin;
    
    public PlayerLoginListener(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        if (e.getResult() == Result.ALLOWED) {
            MadPlayer player = this.plugin.getPlayerManager().addPlayer(e.getPlayer());
            player.downloadData();
            player.downloadSettings();
        }
    }
}
