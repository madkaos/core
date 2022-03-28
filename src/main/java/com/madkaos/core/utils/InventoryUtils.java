package com.madkaos.core.utils;

import com.madkaos.core.math.Vector2;

public class InventoryUtils {
    public static int getSlotByXY (final int x, final int y) {
        return (x - 1) + (9 * (y - 1));
    }

    public static Vector2 getXYBySlot (final int slot) {
        int y = slot / 9;
        int x = slot - (y * 9);
        return new Vector2(x + 1, y + 1);
    }
}