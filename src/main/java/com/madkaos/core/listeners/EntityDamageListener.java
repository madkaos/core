package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.config.Configuration;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EntityDamageListener implements Listener {
    private MadKaosCore plugin;
    private Configuration config;

    public EntityDamageListener(MadKaosCore plugin) {
        this.plugin = plugin;
        this.config = plugin.getMainConfig();
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        MadPlayer player = this.plugin.getPlayerManager().getPlayer((Player) e.getEntity());

        // Void teleport
        if (e.getCause() == DamageCause.VOID) {
            if (config.getBoolean("spawn.enabled") && config.getBoolean("spawn.teleport-on-void")) {
                player.teleport(config.getLocation("spawn.position", true));
                e.setCancelled(true);
            }
        }

        // Cancel damage
        if (this.plugin.isLobby()) {
            e.setCancelled(true);
        }
    }
}
