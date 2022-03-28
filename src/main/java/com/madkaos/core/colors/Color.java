package com.madkaos.core.colors;

import org.bukkit.DyeColor;

public class Color {
    private String displayName;
    private String name;
    private String code;

    public Color(String displayName, String name, String code) {
        this.displayName = displayName;
        this.name = name;
        this.code = code;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public String getColoredDisplayName() {
        return this.code + this.displayName;
    }

    @SuppressWarnings("deprecation")
    public byte getWoolData() {
        return DyeColor.valueOf(this.name).getWoolData();
    }

    @SuppressWarnings("deprecation")
    public byte getDyeData() {
        return DyeColor.valueOf(this.name).getDyeData();
    }
}
