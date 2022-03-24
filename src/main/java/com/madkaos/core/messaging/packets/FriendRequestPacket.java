package com.madkaos.core.messaging.packets;

import com.madkaos.core.messaging.Channel;
import com.madkaos.core.messaging.IPacket;

public class FriendRequestPacket implements IPacket {
    private String author;
    private String target;

    public FriendRequestPacket(String author, String target) {
        this.author = author;
        this.target = target;
    }

    public FriendRequestPacket() {
        this(null, null);
    }

    @Override
    public String getChannel() {
        return Channel.FRIEND_REQUEST_CHANNEL;
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
