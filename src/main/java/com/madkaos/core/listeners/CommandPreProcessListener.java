package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreProcessListener implements Listener {
    private MadKaosCore plugin;
    private int commandCooldown;
    
    public CommandPreProcessListener(MadKaosCore plugin) {
        this.plugin = plugin;
        this.commandCooldown =  plugin.getMainConfig().getInt("chat.command-cooldown");
    }

    @EventHandler(ignoreCancelled = true)
    public void onCommandPreProcess(PlayerCommandPreprocessEvent e) {
        MadPlayer player = this.plugin.getPlayerManager().getPlayer(e.getPlayer());

        if (this.plugin.getMainConfig().getStringList("blocked-commands").contains(e.getMessage().split(" ")[0].toLowerCase())) {
            player.sendI18nMessage("common.no-permission-admin");
            e.setCancelled(true);
            return;
        }
        
        // Command cooldown
        if (player.getLastCommand() < this.commandCooldown) {
            player.sendI18nMessage("command-cooldown");
            e.setCancelled(true);
            return;
        } else {
            player.setLastCommand();
        }
    }
}
