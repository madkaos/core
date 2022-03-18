package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
    private MadKaosCore plugin;

    public PlayerInteractListener(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        e.getPlayer().sendMessage("1");
        MadPlayer player = this.plugin.getPlayerManager().getPlayer(e.getPlayer());
        onInteract(player.getBukkitPlayer(), player.getBukkitPlayer().getInventory().getHeldItemSlot());
    }
    public void onInteract(Player player, int slot) {
        switch (player.getInventory().getHeldItemSlot()) {
            case 0:
                player.performCommand("menu");
        }
    }
}
