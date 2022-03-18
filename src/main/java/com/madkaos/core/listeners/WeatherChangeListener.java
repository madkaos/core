package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {
    private final MadKaosCore plugin;

    public WeatherChangeListener(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
}
