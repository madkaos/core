package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {
    private final MadKaosCore plugin;

    public FoodLevelChangeListener(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (this.plugin.isLobby()) {
            e.setCancelled(true);
        }
    }
}
