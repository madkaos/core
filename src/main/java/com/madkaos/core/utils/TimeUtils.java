package com.madkaos.core.utils;

public class TimeUtils {
    public static String getStringFromMilis(long millis) {
        if (millis == -1) {
            return "(Permanente)";
        }

        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        String time = days + "d " + hours % 24 + "h " + minutes % 60 + "m " + seconds % 60 + "s"; 
        return time;
    }

    public static long getMilisFromTimeArgument(String arg) {
        int multiplier = 0;
        String spliter = null;

        if (arg.endsWith("s")) {
            multiplier = 1000;
            spliter = "s";
        } else if (arg.endsWith("m")) {
            multiplier = 1000 * 60;
            spliter = "m";
        } else if (arg.endsWith("h")) {
            spliter = "h";
            multiplier = 1000 * 60 * 60;
        } else if (arg.endsWith("d")) {
            spliter = "d";
            multiplier = 1000 * 60 * 60 * 24;
        }
        
        // Check for non format and set it to infinite or invalid (-1)
        if (multiplier != 0 && spliter != null) {
            String rawNumber = arg.split(spliter)[0];
            if (StringUtils.isNumerical(rawNumber)) {
                int number = Integer.parseInt(rawNumber);
                return number * multiplier;
            }
        }
       
        System.out.println("woops");
        return -1;
    }
}
