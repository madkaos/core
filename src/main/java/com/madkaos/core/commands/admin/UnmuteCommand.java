package com.madkaos.core.commands.admin;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;

@Command(
    name = "unmute",
    permission = "core.unmute",
    minArguments = 1,
    arguments = { 
        Argument.PLAYER
    }
)
public class UnmuteCommand extends CommandListener {
    @Override
    public void onExecuteByConsole(CommandContext ctx) {
        MadPlayer target = ctx.getArguments().getOfflinePlayer(0);
        target.unban();
    }

    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        MadPlayer target = ctx.getArguments().getOfflinePlayer(0);
        target.unban(player);
    }
}
