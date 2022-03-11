package com.madkaos.core.commands.player;

import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.entities.PlayerSettings;

@Command(
    name = "fly",
    permission = "core.commands.fly",
    isVIP = true
)
public class FlyCommand extends CommandListener {
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
