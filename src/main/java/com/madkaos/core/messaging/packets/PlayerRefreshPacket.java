package com.madkaos.core.messaging.packets;

public class PlayerRefreshPacket {
    private String target;

    public PlayerRefreshPacket(String target) {
        this.target = target;
    }

    public PlayerRefreshPacket() {
        this(null);
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
