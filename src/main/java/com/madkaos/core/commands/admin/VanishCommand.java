package com.madkaos.core.commands.admin;

import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.entities.PlayerSettings;

@Command(
    name = "vanish",
    permission = "core.commands.vanish"
)
public class VanishCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        PlayerSettings settings = player.getSettings();

        boolean result = !settings.vanished;
        player.setVanish(result);
        settings.vanished = result;

        settings.save();

        if (settings.vanished) {
            player.sendI18nMessage("vanish.enabled");
        } else {
            player.sendI18nMessage("vanish.disabled");
        }
    } 
}
