package com.madkaos.core.commands.admin;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.utils.TimeUtils;

@Command(
    name = "ban",
    permission = "core.ban",
    minArguments = 1,
    arguments = { 
        Argument.PLAYER,
        Argument.LARGE_STRING
    }
)
public class BanCommand extends CommandListener {
    private void parseBan(MadPlayer executor, MadPlayer target, String reasonRaw) {
        String[] commandParts = reasonRaw.split(" ", 2);
        long time = -1;
        String reason = "";

        if (commandParts.length == 2) {
            time = TimeUtils.getMilisFromTimeArgument(commandParts[0]);
        }

        if (time == -1) {
            reason = commandParts[0];
        }

        if (commandParts.length == 2) {
            if (reason != "") {
                reason += " ";
            }
            reason += commandParts[1];
        }

        target.unban(executor);
        target.ban(executor, reason, (int) time);
    }
    @Override
    public void onExecuteByConsole(CommandContext ctx) {
        MadPlayer target = ctx.getArguments().getOfflinePlayer(0);
        String reasonRaw = ctx.getArguments().getString(1);

        if (reasonRaw != null) {
            this.parseBan(null, target, reasonRaw);
        } else {
            ctx.getExecutor().sendI18nMessage("ban.usage");
        }
    }

    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        MadPlayer target = ctx.getArguments().getOfflinePlayer(0);
        String reasonRaw = ctx.getArguments().getString(1);

        if (reasonRaw != null) {
            this.parseBan(player, target, reasonRaw);
        } else {
            System.out.println("la gui");
            // Open GUI
        }
    }
}
