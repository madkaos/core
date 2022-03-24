package com.madkaos.core.player;

import com.madkaos.core.MadKaosCore;

import org.bukkit.Location;

public class PendingTeleport {
    private MadKaosCore plugin;
    private MadPlayer player;
    private Location location;
    private int counter;
    private boolean handled;
    private String message;
    
    public PendingTeleport(MadPlayer player, Location location, int time, String message) {
        this.plugin = player.getPlugin();
        this.player = player;
        this.location = location;
        this.counter = time;
        this.message = message;
        this.handled = false;
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
        if (this.handled) {
            return;
        }

        this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {
            this.player.teleport(this.location);
        });

        this.player.sendMessage(this.message);
        this.handled = true;
    }

    public boolean isHandled() {
        return this.handled;
    }
}
