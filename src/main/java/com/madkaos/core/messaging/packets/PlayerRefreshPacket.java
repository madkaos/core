package com.madkaos.core.messaging.packets;

import com.madkaos.core.messaging.Channel;
import com.madkaos.core.messaging.IPacket;

public class PlayerRefreshPacket implements IPacket {
    private String target;

    public PlayerRefreshPacket(String target) {
        this.target = target;
    }

    public PlayerRefreshPacket() {
        this(null);
    }

    @Override
    public String getChannel() {
        return Channel.PLAYER_REFRESH_CHANNEL;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
