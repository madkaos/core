package com.madkaos.core.messaging.packets;

import com.madkaos.core.messaging.Channel;
import com.madkaos.core.messaging.IPacket;

public class FriendAcceptedPacket implements IPacket {
    private String author;
    private String target;

    public FriendAcceptedPacket(String author, String target) {
        this.author = author;
        this.target = target;
    }

    public FriendAcceptedPacket() {
        this(null, null);
    }

    @Override
    public String getChannel() {
        return Channel.FRIEND_ACCEPTED_CHANNEL;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTarget() {
        return this.target;
    }

    public void setAuthor(String message) {
        this.author = message;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
