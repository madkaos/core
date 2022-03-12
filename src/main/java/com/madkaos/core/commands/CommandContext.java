package com.madkaos.core.commands;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.errors.BadArgumentException;
import com.madkaos.core.errors.PlayerNotFoundException;
import com.madkaos.core.errors.PlayerOfflineException;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandContext {
    private CommandExecutor executor;
    private CommandArguments arguments;

    public CommandContext(MadKaosCore plugin, CommandSender sender, Argument[] requiredArguments) {
        if (sender instanceof Player) {
            this.executor = plugin.getPlayerManager().getPlayer((Player) sender);
        } else {
            this.executor = new CommandExecutor(plugin, sender);
        }

        this.arguments = new CommandArguments(plugin, requiredArguments);
    }

    public void parseArguments(String[] args) throws BadArgumentException, PlayerNotFoundException, PlayerOfflineException {
        this.arguments.parse(args);
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

    public CommandArguments getArguments() {
        return this.arguments;
    }
}
