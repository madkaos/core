package com.madkaos.core.messaging.packets;

public class FriendRequestPacket {
    private String author;
    private String target;

    public FriendRequestPacket(String author, String target) {
        this.author = author;
        this.target = target;
    }

    public FriendRequestPacket() {
        this(null, null);
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
