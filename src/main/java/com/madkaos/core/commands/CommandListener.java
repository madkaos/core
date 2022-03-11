package com.madkaos.core.commands;

import com.madkaos.core.MadKaosCore;

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

    // Utils
    public void register(MadKaosCore plugin) {
        this.plugin = plugin;
        this.command = this.getClass().getAnnotation(Command.class);
        plugin.getCommand(this.command.name()).setExecutor(this);
    }

    // Command logic
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        CommandContext ctx = new CommandContext(this.plugin, sender, args);

        if (!command.permission().isEmpty() && !sender.hasPermission(command.permission())) {
            this.onMissingPermission(ctx);
            return true;
        }


        this.onExecute(ctx);
        return true;
    }
}
