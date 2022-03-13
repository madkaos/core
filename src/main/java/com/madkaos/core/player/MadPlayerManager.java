package com.madkaos.core.player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.madkaos.core.MadKaosCore;

import org.bukkit.entity.Player;

public class MadPlayerManager {
    private MadKaosCore plugin;

    private Map<Player, MadPlayer> players;

    public MadPlayerManager(MadKaosCore plugin) {
        this.plugin = plugin;
        this.players = new HashMap<>();
    }

    public MadPlayer addPlayer(Player bukkitPlayer) {
        MadPlayer player = new MadPlayer(this.plugin, bukkitPlayer);
        this.players.put(bukkitPlayer, player);
        return player;
    }

    public MadPlayer removePlayer(Player bukkitPlayer) {
        return this.players.remove(bukkitPlayer);
    }

    public MadPlayer getPlayer(Player bukkitPlayer) {
        return this.players.get(bukkitPlayer);
    }

    public MadPlayer getPlayer(String name) {
        Player bukkitPlayer = this.plugin.getServer().getPlayerExact(name);
        if (bukkitPlayer != null && bukkitPlayer.isOnline()) {
            return this.getPlayer(bukkitPlayer);
        } else {
            return null;
        }
    }

    public Collection<MadPlayer> getPlayers() {
        return this.players.values();
    }

    public void clear() {
        this.players.clear();
    }

    public void addAll() {
        for (Player bukkitPlayer : this.plugin.getServer().getOnlinePlayers()) {
            MadPlayer player = this.addPlayer(bukkitPlayer);
            player.downloadData();
            player.downloadSettings();
            player.setupPlayer();
        }
    }
}
