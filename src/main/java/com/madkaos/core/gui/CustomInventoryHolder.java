package com.madkaos.core.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CustomInventoryHolder implements InventoryHolder {
    private final ItemCollection collection;

    protected CustomInventoryHolder(final ItemCollection collection) {
        this.collection = collection;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
    
    public ItemCollection getItemCollection () {
        return this.collection;
    }
}