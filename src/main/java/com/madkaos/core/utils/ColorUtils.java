package com.madkaos.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

public class ColorUtils {
    public static String colorize(String string)
    {
        Matcher matcher = Pattern.compile("&#[a-fA-F0-9]{6}").matcher(string);
        while (matcher.find())
        {
            String code = matcher.group().replace("&", "");
            string = string.replace("&" + code, net.md_5.bungee.api.ChatColor.of(code) + "");
        }
        string = ChatColor.translateAlternateColorCodes('&', string);
        return string;
    }
}
