package com.madkaos.core.commands.admin;

import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.entities.PlayerSettings;

@Command(
    name = "gamemode",
    permission = "core.commands.gamemode",
    arguments = { 
        Argument.STRING 
    }
)
public class GameModeCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        PlayerSettings settings = player.getSettings();

        boolean result = !settings.fly;
        player.setFlying(result);
        settings.fly = result;

        settings.save();

        if (settings.fly) {
            player.sendI18nMessage("fly.enabled");
        } else {
            player.sendI18nMessage("fly.disabled");
        }
    } 
}
