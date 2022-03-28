package com.madkaos.core.gui;


import java.util.List;

import com.madkaos.core.player.MadPlayer;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryGUIContext {
    private final MadPlayer player;
    private final InventoryGUI gui;

    private final ItemCollection items;
    private final InventoryHolder holder;

    private final Inventory inventory;
    private final int size;

    public InventoryGUIContext (final MadPlayer player, final InventoryGUI gui) {
        this.player = player;
        this.gui = gui;

        this.items = new ItemCollection();
        this.holder = new CustomInventoryHolder(this.items);

        this.size = this.gui.getSize(player);
        this.inventory = Bukkit.createInventory(this.holder, this.size, this.gui.getTitle(player));
    }

    public InventoryGUIContext addItem (final ItemBuilder item) {
        if (this.gui.isAutoRefresh()) {
            item.setModifyAction(() -> {
                this.refresh();
            });
        }

        if (item.getSlot() == -1) {
            item.setSlot(this.getFirstAvailableSlot());
        }

        this.items.addItem(item);
        return this;
    }

    public InventoryGUIContext addItems (final List<ItemBuilder> items) {
        for (final ItemBuilder item : items) {
            this.addItem(item);
        }
        return this;
    }

    public ItemStack addItemStackIfNotExist (final int slot, final ItemStack item) {
        if (slot >= this.inventory.getSize()) {
            return null;
        }

        final ItemBuilder collectionItem = this.items.getItemAtSlot(slot);
        final ItemStack slotItem = this.inventory.getItem(slot);

        if (collectionItem == null && slotItem == null) {
            this.inventory.setItem(slot, item);
            return item;
        }

        return null;
    }

    public void clearSlot (final int slot) {
        this.items.clearSlot(slot);
    }

    public InventoryFill fill (final ItemBuilder item) {
        final InventoryFill inventoryFill = new InventoryFill(this);

        for (int i = 0; i < this.inventory.getSize(); i++) {
            inventoryFill.addItem(item.clone().setSlot(i));
        }
        
        this.addItems(inventoryFill.getItems());
        return inventoryFill;
    }

    public InventoryGUIContext fillVerticalLine (final int x, final ItemStack fillItem) {
        for (int i = 0; i < 6; i++) {
            final int slot = (i * 9) + (x - 1) ;//(x - 1) * i;
            this.addItemStackIfNotExist(slot, fillItem);
        }
        return this;
    }

    public InventoryGUIContext fillVerticalLine (final int x, final ItemBuilder item) {
        return this.fillVerticalLine(x, item.build());
    }

    public InventoryGUIContext fillHorizontalLine (final int y, final ItemStack fillItem) {
        for (int i = 0; i < 9; i++) {
            final int slot = (9 * (y - 1)) + i;
            this.addItemStackIfNotExist(slot, fillItem);
        }
        return this;
    }

    public InventoryGUIContext fillHorizontalLine (final int y, final ItemBuilder item) {
        return this.fillHorizontalLine(y, item.build());
    }

    public InventoryGUIContext fillBorders (final ItemBuilder item) {
        final ItemStack fillItem = item.build();

        this.fillHorizontalLine(1, fillItem); // Fill top border
        this.fillHorizontalLine(this.getLastVerticalRow(), fillItem); // Fill bottom border
        this.fillVerticalLine(1, fillItem); // Fill left border
        this.fillVerticalLine(9, fillItem); // Fill right border
        
        return this;
    }

    public int getLastVerticalRow () {
        return this.size / 9;
    }

    public MadPlayer getPlayer () {
        return this.player;
    }

    public InventoryGUI getGUI () {
        return this.gui;
    }

    public ItemCollection getItemCollection () {
        return this.items;
    }

    public InventoryHolder getHolder () {
        return this.holder;
    }

    public Inventory getInventory () {
        return this.inventory;
    }

    public int getFirstAvailableSlot() {
        for (int i = 0; i < this.size; i++) {
            if (this.getItemCollection().getItemAtSlot(i) == null && this.inventory.getItem(i) == null) {
                return i;
            }
        }

        return -1;
    }

    public Inventory buildInventory () {
        this.getItemCollection().build(this.getInventory());
        return this.inventory;
    }

    public InventoryGUIContext refresh () {
        this.items.refresh(this.getInventory());
        return this;
    }
}