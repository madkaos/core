package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import items.Item;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private MadKaosCore plugin;
    
    public PlayerJoinListener(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    private void handleSpawnTP(MadPlayer player) {
        if (plugin.getMainConfig().getBoolean("spawn.teleport-on-join")) {
            Location loc = plugin.getMainConfig().getLocation("spawn.position", true);
            player.getBukkitPlayer().teleport(loc);
        }
    }

    private void handleMotd(MadPlayer player) {
        if (this.plugin.isLobby()) {
            player.sendMessage(this.plugin.getMainConfig().getString("motd"));
        }
    }

    private String handleJoinMessage(MadPlayer player) {
        if (player.getBukkitPlayer().hasPermission("core.join-message") && !player.isVanished() && this.plugin.isLobby()) {
            return player.formatMessage(player.getI18nMessage("join-message"));
        } else {
            return null;
        }
    }

    private void givePlayerItems(MadPlayer player){
        if (this.plugin.isLobby()) {
            new Item(player, Material.COMPASS, "items.games.name", "items.games.lore").giveItems(0);
            new Item(player, Material.valueOf("SKULL_ITEM"), "items.profile.name", "items.profile.lore").giveItems(1);
            new Item(player, Material.NETHER_STAR, "items.lobby.name", "items.lobby.lore").giveItems(8);
        }
    }

    private void handleVanish(MadPlayer player) {
        for (MadPlayer mp : this.plugin.getPlayerManager().getPlayers()) {
            if (mp.isVanished()) {
                player.getBukkitPlayer().hidePlayer(this.plugin, mp.getBukkitPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        MadPlayer player = this.plugin.getPlayerManager().getPlayer(e.getPlayer());
        
        String currentAddress = player.getAddress();
        
        if (player.getData().address == null || player.getData().address != currentAddress) {
            player.getData().address = currentAddress;
            player.getData().save();
        }

        this.handleMotd(player);
        this.handleVanish(player);
        this.handleSpawnTP(player);

        if (this.plugin.isLobby()) {
            player.getBukkitPlayer().getInventory().clear();
            givePlayerItems(player);

            player.getBukkitPlayer().setFoodLevel(20);
            player.getBukkitPlayer().setHealth(20);
            player.getBukkitPlayer().getInventory().setHeldItemSlot(0);
        }

        e.setJoinMessage(handleJoinMessage(player));

        player.setupPlayer();
        e.setJoinMessage(handleJoinMessage(player));
    }
}
