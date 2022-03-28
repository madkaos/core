package com.madkaos.core.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class InventoryFill {

    private final InventoryGUIContext gui;
    private final List<ItemBuilder> items;

    private boolean show = true;

    protected InventoryFill (final InventoryGUIContext gui) {
        this.gui = gui;
        this.items = new ArrayList<>();
    }

    public void addItem (final ItemBuilder item) {
        if (item != null) {
            if (item.getSlot() < this.gui.getInventory().getSize()) {
                if (this.gui.getInventory().getItem(item.getSlot()) == null) {
                    if (this.gui.getItemCollection().getItemAtSlot(item.getSlot()) == null) {
                        this.items.add(item);
                    }
                }
            }
        }
    }

    public void setShow (final boolean show) {
        for (final ItemBuilder item : this.items) {
            item.setShow(show);
        }
        this.show = show;
    }

    public void hide () {
        this.setShow(false);
    }

    public void show () {
        this.setShow(true);
    }

    public boolean isShow () {
        return this.show;
    }

    public void changeMaterial (final Material material) {
        for (final ItemBuilder item : this.items) {
            item.setType(material);
        }
    }

    public List<ItemBuilder> getItems () {
        return this.items;
    }
}