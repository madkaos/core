package com.madkaos.core.colors;

import java.util.ArrayList;
import java.util.List;

public class ColorMap {
    public static final List<Color> COLORS = new ArrayList<Color>() {{
        add(new Color("Rojo", "RED", "§c"));
        add(new Color("Naranja", "ORANGE", "§6"));
        add(new Color("Amarillo", "YELLOW", "§e"));

        add(new Color("Lima", "LIME", "§a"));
        add(new Color("Verde", "GREEN", "§2"));

        add(new Color("Cyan", "LIGHT_BLUE", "§b"));
        add(new Color("Aqua", "CYAN", "§3"));
        add(new Color("Azul", "BLUE", "§1"));

        add(new Color("Morado", "PURPLE", "§5"));
        add(new Color("Rosa", "PINK", "§d"));

        add(new Color("Blanco", "WHITE", "§f"));
        add(new Color("Gris", "GRAY", "§7"));
    }};
}
