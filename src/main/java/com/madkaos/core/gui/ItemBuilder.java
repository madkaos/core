package com.madkaos.core.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.madkaos.core.utils.InventoryUtils;

public class ItemBuilder {
    /* Data storage */
    private ItemStack itemDragged;
    private boolean modified = false;

    /* Item props */
    private int amount = -1;
    private int data = 0;
    private boolean draggable = false;
    private List<String> lore;
    private Material material;
    private String name;

    /* Special item props */
    private ItemStack itemstack;

    /* In inventory data */
    private int slot = -1;
    private boolean show = true;

    /* Actions */
    private Runnable dragInAction;
    private Runnable dragOutAction;
    private Runnable leftClickAction;
    private Runnable rightClickAction;
    private Runnable modifyAction;

    /* Constructors */
    public ItemBuilder(final ItemStack stack) {
        this.itemstack = stack;
    }

    public ItemBuilder(final Material material) {
        this.material = material;
    }

    public ItemBuilder(final String material) {
        this.material = Material.matchMaterial(material);
    }

    public ItemBuilder() {
        this.material = null;
    }

    public ItemBuilder clone() {
        ItemBuilder item = null;
        if (this.itemstack != null) {
            item = new ItemBuilder(this.itemstack);
        } else {
            item = new ItemBuilder(this.material);
        }

        return item.setName(this.name)
                .setAmount(this.amount)
                .setData(this.data)
                .setDragInAction(this.dragInAction)
                .setDragOutAction(this.dragOutAction)
                .setDraggable(this.draggable)
                .setLeftClickAction(this.leftClickAction)
                .setLore(this.lore)
                .setModifyAction(this.modifyAction)
                .setSlot(this.slot)
                .setRightClickAction(this.rightClickAction)
                .setShow(this.show)
                .setModified(this.modified);
    }

    /* Getters */
    public boolean isDraggable() {
        return this.draggable;
    }

    public boolean isEmpty() {
        return this.material == null || this.material == Material.AIR;
    }

    public boolean isModified() {
        return this.modified;
    }

    public boolean isShow() {
        return this.show;
    }

    public ItemStack getItemDragged() {
        return this.itemDragged;
    }

    public int getSlot() {
        return this.slot;
    }

    /* Setters */
    public ItemBuilder setAmount(final int amount) {
        this.amount = amount;
        this.setModified(true);
        return this;
    }

    public ItemBuilder setData(final int data) {
        this.data = data;
        this.setModified(true);
        return this;
    }

    public ItemBuilder setDraggable(final boolean draggable) {
        this.draggable = draggable;
        this.setModified(true);
        return this;
    }

    public ItemBuilder setLore(final List<String> lore) {
        this.lore = lore;
        this.setModified(true);
        return this;
    }

    public ItemBuilder setLore(final String lore) {
        this.lore = new ArrayList<>();
        for (final String line : lore.split("\n")) {
            this.lore.add(line);
        }
        this.setModified(true);
        return this;
    }

    public ItemBuilder setName(final String name) {
        this.name = name;
        this.setModified(true);
        return this;
    }

    public ItemBuilder setSlot(final int slot) {
        this.slot = slot;
        this.setModified(true);
        return this;
    }

    public ItemBuilder setPosition(final int x, final int y) {
        if (x == -1 || y == -1) {
            return this.setSlot(-1);
        } else {
            return this.setSlot(InventoryUtils.getSlotByXY(x, y));
        }
    }

    public ItemBuilder setShow(final boolean show) {
        this.show = show;
        this.setModified(true);
        return this;
    }

    public ItemBuilder setDragInAction(final Runnable runnable) {
        this.dragInAction = runnable;
        return this;
    }

    public ItemBuilder setDragOutAction(final Runnable runnable) {
        this.dragOutAction = runnable;
        return this;
    }

    public ItemBuilder setLeftClickAction(final Runnable runnable) {
        this.leftClickAction = runnable;
        return this;
    }

    public ItemBuilder setRightClickAction(final Runnable runnable) {
        this.rightClickAction = runnable;
        return this;
    }

    public ItemBuilder setModifyAction(final Runnable runnable) {
        this.modifyAction = runnable;
        return this;
    }

    public ItemBuilder setType(final Material type) {
        this.material = type;
        this.setModified(true);
        return this;
    }

    /* Methods */
    public ItemBuilder handleLeftClick() {
        if (this.leftClickAction != null) {
            this.leftClickAction.run();
        }
        return this;
    }

    public ItemBuilder handleRightClick() {
        if (this.rightClickAction != null) {
            this.rightClickAction.run();
        }
        return this;
    }

    public ItemBuilder handleDragIn(final ItemStack item) {
        this.itemDragged = item;
        if (this.dragInAction != null) {
            this.dragInAction.run();
        }
        return this;
    }

    public ItemBuilder handleDragOut() {
        this.itemDragged = null;
        if (this.dragOutAction != null) {
            this.dragOutAction.run();
        }
        return this;
    }

    public void handleModifyAction() {
        if (this.modifyAction != null) {
            this.modifyAction.run();
        }
    }

    public ItemBuilder setModified(final boolean modified) {
        this.modified = modified;
        if (this.modified) {
            this.handleModifyAction();
        }
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemStack build() {
        if (this.isEmpty() || !this.show) {
            return null;
        }

        ItemStack item = null;

        if (this.itemstack == null) {
            item = new ItemStack(this.material, amount == -1 ? 1 : amount, (short) this.data);
        } else {
            item = itemstack.clone();
            item.setAmount(amount == -1 ? item.getAmount() : amount);
        }

        final ItemMeta meta = item.getItemMeta();
        meta.setLore(this.lore);
        meta.setDisplayName(this.name);
        item.setItemMeta(meta);
        this.setModified(false);
        return item;
    }
}