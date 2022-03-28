package com.madkaos.core.gui;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.inventory.Inventory;

public class ItemCollection {
    
    private List<ItemBuilder> items;
    private List<ItemBuilder> deleted;

    public ItemCollection () {
        this.items = new ArrayList<>();
        this.deleted = new ArrayList<>();
    }

    public ItemCollection addItem (final ItemBuilder item) {
        this.items.add(item);
        return this;
    }

    public ItemCollection build (final Inventory inventory) {
        for (final ItemBuilder item : this.items) {
            if (item.isShow()) {
                inventory.setItem(item.getSlot(), item.build());
            }
        }
        return this;
    }

    public void clearSlot (final int slot) {
        final Iterator<ItemBuilder> iterator = this.items.iterator();
        while (iterator.hasNext()) {
            final ItemBuilder item = iterator.next();
            if (item.getSlot() == slot) {
                this.deleted.add(item);
                iterator.remove();
                break;
            }
        }
    }

    public ItemCollection refresh (final Inventory inventory) {
        for (final ItemBuilder item : this.items) {
            if (item.isDraggable()) {
                continue;
            }

            if (item.isModified() || inventory.getItem(item.getSlot()) == null) {
                inventory.setItem(item.getSlot(), item.build());
            }
        }

        for (final ItemBuilder deletedItem : this.deleted) {
            inventory.setItem(deletedItem.getSlot(), null);
        }

        deleted.clear();
        return this;
    }

    public ItemCollection clear () {
        this.items.clear();
        return this;
    }

    public List<ItemBuilder> getItems () {
        return this.items;
    }

    public ItemBuilder getItemAtSlot (final int slot) {
        for (final ItemBuilder item : this.items) {
            if (item.getSlot() == slot) {
                return item;
            }
        }
        return null;
    }
}