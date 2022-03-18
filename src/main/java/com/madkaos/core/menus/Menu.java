package com.madkaos.core.menus;

import com.madkaos.core.player.MadPlayer;
import items.Item;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class Menu {
    private final Inventory inventory;
    private final MadPlayer player;

    public Menu(MadPlayer player, int size, String title) {
        inventory = Bukkit.createInventory(null, size, title);
        this.player = player;
    }
    public void addItem(int slot, Item item) {
        inventory.setItem(slot, item.getItemStack());
    }
    public void openMenu() {
        player.getBukkitPlayer().openInventory(inventory);
    }
}
