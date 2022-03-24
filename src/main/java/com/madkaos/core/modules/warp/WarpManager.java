package com.madkaos.core.modules.warp;

import java.util.ArrayList;
import java.util.List;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.config.Configuration;

import org.bukkit.Location;

public class WarpManager {
    private MadKaosCore core;
    private List<Warp> warps;

    public WarpManager(MadKaosCore core) {
        this.core = core;
        this.warps = new ArrayList<>();
        
        this.load();
    }

    public Warp getWarp(String name) {
        for (Warp warp : this.warps) {
            if (warp.getName().equalsIgnoreCase(name)) {
                return warp;
            }
        }

        return null;
    }

    public Warp createWarp(String name, Location location) {
        if (this.getWarp(name) == null) {
            Configuration config = this.core.getWarpConfig();
            Warp warp = new Warp(name, location);
            this.warps.add(warp);
            config.setLocation(name + ".position", location);
            config.safeSave();
            return warp;
        }

        return null;
    }


    public Warp deleteWarp(String name) {
        Warp warp = this.getWarp(name);

        if (this.getWarp(name) != null) {
            Configuration config = this.core.getWarpConfig();
            this.warps.remove(warp);
            config.set(warp.getName() + ".position", null);
            config.safeSave();
        }

        return null;
    }

    public String getWarpsAsString() {
        String output = "";

        for (Warp warp : this.warps) {
            if (output != "") {
                output = ", ";
            }

            output += warp.getName();
        }

        return output;
    }

    public void load() {
        Configuration config = this.core.getWarpConfig();
        this.warps.clear();

        for (String warpName : config.getKeys(false)) {
            if (!config.contains(warpName + ".position")) {
                continue;
            }

            Warp warp = new Warp(warpName, config.getLocation(warpName + ".position", true));
            this.warps.add(warp);
        }
    }
}
