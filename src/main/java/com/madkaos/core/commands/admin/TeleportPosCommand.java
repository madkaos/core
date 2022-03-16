package com.madkaos.core.commands.admin;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;

@Command(name = "tpos", permission = "core.tpos", minArguments = 3, arguments = {
        Argument.INT,
        Argument.INT,
        Argument.INT
})
public class TeleportPosCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        int x = ctx.getArguments().getInt(0);
        int y = ctx.getArguments().getInt(1);
        int z = ctx.getArguments().getInt(2);

        ctx.getPlayer().teleport(x, y, z);
    }
}
