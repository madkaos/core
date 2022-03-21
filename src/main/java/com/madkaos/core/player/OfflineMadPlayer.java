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

    @Override
    public String getUUID() {
        if (this.data == null) {
            return null;
        }
        
        return this.data.uuid;
    }
    
    /* Initial methods */
    @Override
    public void downloadData() {
        if (this.isOnline()) {
            super.downloadData();
            return;
        }

        this.data = this.plugin.getPlayerDataRepository().findOne(MapFactory.create("username", name));
    }
}
