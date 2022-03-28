package com.madkaos.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import com.madkaos.core.gui.CustomInventoryHolder;
import com.madkaos.core.gui.ItemBuilder;
import com.madkaos.core.gui.ItemCollection;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onInventoryClose (final InventoryCloseEvent e) {
        final Inventory inventory = e.getInventory();
        final InventoryHolder holder = inventory.getHolder();

        if (!(holder instanceof CustomInventoryHolder)) {
            return;
        }

        final ItemCollection itemCollection = ((CustomInventoryHolder) holder).getItemCollection();
        for (final ItemBuilder item : itemCollection.getItems()) {
            if (item.getItemDragged() != null && item.isDraggable()) {
                e.getPlayer().getInventory().addItem(item.getItemDragged());
            }
        }
    }
}