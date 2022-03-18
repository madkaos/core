package com.madkaos.core.modules.warp;

import org.bukkit.Location;

public class Warp {
    private String name;
    private Location location;

    public Warp(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }
}
