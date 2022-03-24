package com.madkaos.core.messaging.packets;

import com.madkaos.core.messaging.Channel;
import com.madkaos.core.messaging.IPacket;

public class ReportPacket implements IPacket {
    private String author;
    private String target;
    private String reason;

    public ReportPacket(String author, String target, String reason) {
        this.author = author;
        this.target = target;
        this.reason = reason;
    }

    public ReportPacket() {
        this(null, null, null);
    }

    @Override
    public String getChannel() {
        return Channel.REPORT_CHANNEL;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTarget() {
        return this.target;
    }

    public String getReason() {
        return this.reason;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
