package com.madkaos.core.messaging.packets;

import com.madkaos.core.messaging.Channel;
import com.madkaos.core.messaging.IPacket;
import com.madkaos.core.player.entities.PlayerPunishment;

public class PlayerPunishPacket implements IPacket {
    private String emisor;
    private String target;
    private String reason;
    private int type;
    private long expiresOn;
    private boolean ofIP = false;

    public PlayerPunishPacket(String emisor, String target, String reason, int type, long expiresOn, boolean ofIP) {
        this.emisor = emisor;
        this.target = target;
        this.reason = reason;
        this.type = type;
        this.expiresOn = expiresOn;
        this.ofIP = ofIP;
    }

    public PlayerPunishPacket(String emisor, String target, PlayerPunishment punishment) {
        this(emisor, target, punishment.reason, punishment.type, punishment.expiresOn, punishment.ofIP);
    }

    public PlayerPunishPacket() {
        this(null, null, null, 0, 0, false);
    }

    @Override
    public String getChannel() {
        return Channel.PUNISHMENT_CHANNEL;
    }

    public String getEmisor() {
        return this.emisor;
    }

    public String getReason() {
        return this.reason;
    }

    public int getType() {
        return this.type;
    }

    public long getExpiresOn() {
        return this.expiresOn;
    }

    public boolean isOfIP() {
        return this.ofIP;
    }

    public String getTarget() {
        return this.target;
    }
}
