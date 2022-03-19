package com.madkaos.core.player;

import org.bukkit.Location;

public class PendingTeleport {
    private MadPlayer player;
    private Location location;
    private int counter;
    
    public PendingTeleport(MadPlayer player, Location location, int time) {
        this.player = player;
        this.location = location;
        this.counter = time;
    }

    public void cancel() {
        this.counter = -1;
    }

    public void tick() {
        if (this.counter >= 0) {
            this.counter--;
        }

        if (this.counter == 0) {
            this.handle();
        }
    }

    public void handle() {
        this.player.teleport(this.location);
        this.player.sendI18nMessage("common.teleported");
    }
}
