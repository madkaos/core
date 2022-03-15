package com.madkaos.core.utils;

import com.madkaos.core.player.entities.PlayerPunishment;

public class PunishmentsUtil {
    public static boolean isActive(PlayerPunishment punishment) {
        // Check if punishment was revoked by a staff.
        if (punishment.revokedId != null) {
            return false;
        }

        // Check if punishment is permanent.
        if (punishment.expiresOn == -1) {
            return true;
        }

        // Check for punishment expiration.
        return getRemainingTime(punishment.createdOn, punishment.expiresOn) > 0;
    }

    public static long getRemainingTime(long createdOn, long expiresOn) {
        return Math.max(0, (createdOn + expiresOn) - System.currentTimeMillis());
    }
}
