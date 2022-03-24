package com.madkaos.core.player;

public class PlayerFilter {
    public final static int ANYBODY = 0;
    public final static int ONLY_FRIENDS = 1;
    public final static int NOBODY = 2;

    public final static int next(int currentStatus) {
        if (currentStatus == 2) {
           return 0;
        } else {
            return currentStatus++;
        }
    }
}
