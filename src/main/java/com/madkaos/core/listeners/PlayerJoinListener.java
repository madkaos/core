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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        this.plugin.getPlayerManager().addPlayer(e.getPlayer());

        for (MadPlayer mp : this.plugin.getPlayerManager().getPlayers()) {
            if (mp.isVanished()) {
                e.getPlayer().hidePlayer(this.plugin, mp.getBukkitPlayer());
            }
        }
    }
}
