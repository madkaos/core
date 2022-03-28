package com.madkaos.core.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import com.madkaos.core.gui.CustomInventoryHolder;
import com.madkaos.core.gui.ItemBuilder;
import com.madkaos.core.gui.ItemCollection;

public class InventoryClickListener implements Listener {
    private boolean handleClick(final Inventory inventory, final int slot, final ClickType click) {
        final InventoryHolder holder = inventory.getHolder();
        final ItemCollection itemCollection = ((CustomInventoryHolder) holder).getItemCollection();
        final ItemBuilder item = itemCollection.getItemAtSlot(slot);

        if (item != null && click == ClickType.LEFT) {
            item.handleLeftClick();
        } else if (item != null && click == ClickType.RIGHT) {
            item.handleRightClick();
        }

        if (item == null || !item.isDraggable()) {
            return true;
        } else {
            return false;
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player
                && (e.getSlotType() == SlotType.CONTAINER || e.getSlotType() == SlotType.QUICKBAR)) {
            final Inventory clickedInventory = e.getClickedInventory();
            final InventoryHolder clickedHolder = clickedInventory.getHolder();

            if (clickedHolder instanceof CustomInventoryHolder) {
                if (this.handleClick(e.getClickedInventory(), e.getSlot(), e.getClick())) {
                    e.setCancelled(true);
                    e.setResult(Result.DENY);
                }
            }
        }
    }
}