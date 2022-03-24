package com.madkaos.core.commands.player;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.entities.PlayerHome;

@Command(
    name = "sethome",
    arguments = { Argument.STRING },
    permission = "core.sethome",
    minArguments = 1,
    onlyGame = true
)
public class SetHomeCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        String homeName = ctx.getArguments().getString(0);
        if (player.getHomes().size() >= 3) {
            player.sendI18nMessage("sethome.too-many");
        } else {
            PlayerHome home = player.createHome(homeName);
            if (home != null) {
                player.sendMessage(
                    player.getI18nMessage("sethome.created")
                        .replace("{home}", home.name)
                );
            } else {
                player.sendI18nMessage("sethome.already-exist");
            }
        }
    } 
}
