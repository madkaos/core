package com.madkaos.core.commands.player;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;

@Command(name = "server", arguments = { Argument.STRING }, minArguments = 1, permission = "core.server")
public class ServerCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        String server = ctx.getArguments().getString(0);

        player.sendMessage(
            player.getI18nMessage("server.sending")
                .replace("{server}", server)
        );
        player.sendToServer(server);
    }
}
