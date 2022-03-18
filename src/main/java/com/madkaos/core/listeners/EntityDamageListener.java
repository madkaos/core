package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    private final MadKaosCore plugin;

    public EntityDamageListener(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (this.plugin.isLobby()) {
            e.setCancelled(true);
        }
    }
}
