package com.madkaos.core.player;

import com.dotphin.milkshakeorm.utils.MapFactory;

import com.madkaos.core.MadKaosCore;

import org.bukkit.Bukkit;

public class OfflineMadPlayer extends MadPlayer {
    private String name;
    
    public OfflineMadPlayer(MadKaosCore plugin, String name) {
        super(plugin, Bukkit.getPlayer(name));
        this.name = name;
    }

    public boolean exist() {
        return this.data != null;
    }

    public boolean isOnline() {
        return this.bukkitPlayer != null && this.bukkitPlayer.isOnline();
    }

    /* Initial methods */
    @Override
    public void setupPlayer() {
        if (this.isOnline()) {
            super.setupPlayer();
            return;
        }

        this.downloadData();
        this.downloadSettings();
        this.setupPlayer();
    }

    @Override
    void downloadData() {
        if (this.isOnline()) {
            super.downloadData();
            return;
        }

        this.data = this.plugin.getPlayerDataRepository().findOne(MapFactory.create("username", name));
    }
}
