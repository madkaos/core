package com.madkaos.core.commands;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandContext {
    private CommandExecutor executor;

    public CommandContext(MadKaosCore plugin, CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            this.executor = plugin.getPlayerManager().getPlayer((Player) sender);
        } else {
            this.executor = new CommandExecutor(plugin, sender);
        }
    }

    public CommandExecutor getExecutor() {
        return this.executor;
    }

    public MadPlayer getPlayer() {
        return (MadPlayer) this.executor;
    }

    public boolean isPlayer() {
        return this.executor.isPlayer();
    }
}
