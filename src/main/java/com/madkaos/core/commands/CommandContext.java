package com.madkaos.core.commands;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.command.CommandSender;

public class CommandContext {
    private CommandExecutor executor;

    public CommandContext(MadKaosCore plugin, CommandSender sender, String[] args) {
        this.executor = new CommandExecutor(plugin, sender);
    }

    public CommandExecutor getExecutor() {
        return this.executor;
    }

    public MadPlayer getPlayer() {
        return this.executor.toPlayer();
    }

    public boolean isPlayer() {
        return this.executor.isPlayer();
    }
}
