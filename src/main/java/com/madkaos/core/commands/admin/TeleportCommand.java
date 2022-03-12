package com.madkaos.core.commands.admin;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;

@Command(name = "tp", permission = "core.commands.tp", minArguments = 1, arguments = {
        Argument.ONLINE_PLAYER
})
public class TeleportCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getArguments().getPlayer(0);

        ctx.getPlayer().getBukkitPlayer().teleport(player.getBukkitPlayer().getLocation());
    }
}
