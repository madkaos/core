package com.madkaos.core.messaging.packets;

import com.madkaos.core.messaging.Channel;
import com.madkaos.core.messaging.IPacket;

public class MessagePacket implements IPacket {
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

    @Override
    public String getChannel() {
        return Channel.MESSAGE_CHANNEL;
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
