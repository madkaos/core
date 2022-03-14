package com.madkaos.core.messaging;

public class Channel {
    private static final String PREFIX = "madkaos_";

    public static final String MESSAGE_CHANNEL = PREFIX + "message";
    public static final String FRIEND_REQUEST_CHANNEL = PREFIX + "friend_Request";
    public static final String FRIEND_ACCEPTED_CHANNEL = PREFIX + "friend_accepted";
    public static final String PLAYER_REFRESH_CHANNEL = PREFIX + "player_refresh";
    public static final String REPORT_CHANNEL = PREFIX + "report";
    public static final String PUNISHMENT_CHANNEL = PREFIX + "punishment";
}
