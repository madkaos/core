package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    private final MadKaosCore plugin;

    public InventoryClickListener(MadKaosCore plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (this.plugin.isLobby()) {
            e.setCancelled(true);
        }
    }
}
