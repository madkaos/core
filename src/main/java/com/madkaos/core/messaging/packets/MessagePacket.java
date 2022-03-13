package com.madkaos.core.messaging.packets;

public class MessagePacket {
    private String target;
    private String message;

    public MessagePacket(String target, String message) {
        this.target = target;
        this.message = message;
    }

    public MessagePacket() {
        this(null, null);
    }

    public String getTarget() {
        return this.target;
    }

    public String getMessage() {
        return this.message;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
