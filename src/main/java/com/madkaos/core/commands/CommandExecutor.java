package com.madkaos.core.commands;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.utils.ColorUtils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecutor {
    private MadKaosCore plugin;
    private CommandSender sender;
    
    public CommandExecutor(MadKaosCore plugin, CommandSender sender) {
        this.plugin = plugin;
        this.sender = sender;
    }

    public String formatMessage(String message) {
        return ColorUtils.colorize(message);
    }

    public String getI18nMessage(String key) {
        return this.plugin.getMessages().getString(key);
    }

    public void sendMessage(String message) {
        this.sender.sendMessage(this.formatMessage(message));
    }

    public void sendI18nMessage(String key) {
        this.sendMessage(this.getI18nMessage(key));
    }

    public boolean isPlayer() {
        return this.sender instanceof Player;
    }
}
