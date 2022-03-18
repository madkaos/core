package com.madkaos.core.commands.admin;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;

@Command(name = "tphere", permission = "core.tphere", minArguments = 1, arguments = {
        Argument.ONLINE_PLAYER
})
public class TeleportHereCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        MadPlayer target = ctx.getArguments().getPlayer(0);

        player.sendMessage(
            player.getI18nMessage("tphere.teleported")
                .replace("{player}", target.getBukkitPlayer().getName())
        );
        target.getBukkitPlayer().teleport(player.getBukkitPlayer().getLocation());
    }
}
