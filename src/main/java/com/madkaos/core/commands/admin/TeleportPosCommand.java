package com.madkaos.core.commands.admin;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;

@Command(name = "tpos", permission = "core.tpos", minArguments = 3, arguments = {
        Argument.INT,
        Argument.INT,
        Argument.INT
})
public class TeleportPosCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();

        int x = ctx.getArguments().getInt(0);
        int y = ctx.getArguments().getInt(1);
        int z = ctx.getArguments().getInt(2);

        player.teleport(x, y, z);
        player.sendMessage(
            player.getI18nMessage("tp.teleport")
                .replace("{x}", x + "")
                .replace("{y}", y + "")
                .replace("{z}", z + "")
        );
    }
}
