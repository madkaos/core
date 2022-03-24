package com.madkaos.core.commands.player;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.entities.PlayerHome;

@Command(
    name = "delhome",
    arguments = { Argument.STRING },
    permission = "core.delhome",
    minArguments = 1,
    onlyGame = true
)
public class DelHomeCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        String homeName = ctx.getArguments().getString(0);
        PlayerHome home = player.deleteHome(homeName);
        if (home != null) {
            player.sendMessage(
                player.getI18nMessage("delhome.deleted")
                    .replace("{home}", home.name)
            );
        } else {
            player.sendI18nMessage("home.not-exist");
        }
    } 
}
