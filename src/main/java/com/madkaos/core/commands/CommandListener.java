package com.madkaos.core.commands;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.errors.BadArgumentException;
import com.madkaos.core.errors.PlayerNotFoundException;
import com.madkaos.core.errors.PlayerOfflineException;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class CommandListener implements CommandExecutor {
    protected MadKaosCore plugin;
    protected Command command;

    // For override
    protected void onExecuteByPlayer(CommandContext ctx) {
        ctx.getExecutor().sendI18nMessage("common.no-by-player");
    }
    
    protected void onExecuteByconsole(CommandContext ctx) {
        ctx.getExecutor().sendI18nMessage("common.no-by-console");
    }

    protected void onExecute(CommandContext ctx) {
        if (ctx.isPlayer()) {
            this.onExecuteByPlayer(ctx);
        } else {
            this.onExecuteByconsole(ctx);
        }
    }

    protected void onMissingPermission(CommandContext ctx) {
        if (command.isVIP()) {
            ctx.getExecutor().sendI18nMessage("common.no-permission-vip");
        } else {
            ctx.getExecutor().sendI18nMessage("common.no-permission-admin");
        }
    }

    protected void onBadUsage(CommandContext ctx) {
        ctx.getExecutor().sendI18nMessage(command.name().toLowerCase() + ".usage");
    }

    // Utils
    public void register(MadKaosCore plugin) {
        this.plugin = plugin;
        this.command = this.getClass().getAnnotation(Command.class);
        plugin.getCommand(this.command.name()).setExecutor(this);
    }

    // Command logic
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        CommandContext ctx = new CommandContext(this.plugin, sender, command.arguments());

        if (!command.permission().isEmpty() && !sender.hasPermission(command.permission())) {
            this.onMissingPermission(ctx);
            return true;
        }

        if (command.minArguments() < args.length) {
            this.onBadUsage(ctx);
            return true;
        }

        try {
            ctx.parseArguments(args);
            this.onExecute(ctx);
        } catch (BadArgumentException | PlayerNotFoundException | PlayerOfflineException e) {
            
        }
        
        return true;
    }
}
