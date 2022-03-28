package com.madkaos.core.gui;

import com.madkaos.core.player.MadPlayer;

import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class InventoryGUI {

    public abstract void init(final InventoryGUIContext context);

    public abstract String getTitle(final MadPlayer player);

    public abstract int getSize(final MadPlayer player);

    public boolean isAutoRefresh() {
        return true;
    }

    public void open(final MadPlayer player) {
        final InventoryGUIContext context = new InventoryGUIContext(player, this);
        this.init(context);
        player.getBukkitPlayer().openInventory(context.buildInventory());
    }

    public void openAsync(final Plugin plugin, final MadPlayer player) {
        final InventoryGUIContext context = new InventoryGUIContext(player, this);

        new BukkitRunnable() {
            @Override
            public void run() {
                init(context);
                final Inventory inv = context.buildInventory();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.getBukkitPlayer().openInventory(inv);
                    }
                }.runTask(plugin);
            }
        }.runTaskAsynchronously(plugin);
    }
}