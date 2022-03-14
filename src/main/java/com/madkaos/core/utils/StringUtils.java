package com.madkaos.core.utils;

public class StringUtils {
    public static boolean isNumerical(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
