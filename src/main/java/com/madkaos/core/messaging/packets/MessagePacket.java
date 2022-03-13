package com.madkaos.core.messaging.packets;

public class MessagePacket {
    private String author;
    private String target;
    private String message;

    public MessagePacket(String author, String target, String message) {
        this.author = author;
        this.target = target;
        this.message = message;
    }

    public MessagePacket() {
        this(null, null, null);
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTarget() {
        return this.target;
    }

    public String getMessage() {
        return this.message;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
