package com.madkaos.core.commands.admin;

import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.GameMode;

@Command(
    name = "gamemode",
    permission = "core.commands.gamemode",
    minArguments = 1,
    arguments = { 
        Argument.STRING 
    }
)
public class GameModeCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        String arg = ctx.getArguments().getString(0);
        GameMode mode = null;


        if (arg == "3" || arg.startsWith("sp")) {
            mode = GameMode.SPECTATOR;
        } 
        else if (arg == "2" || arg.startsWith("a")) {
            mode = GameMode.ADVENTURE;
        }
        else if (arg == "1" || arg.startsWith("c")) {
            mode = GameMode.CREATIVE;
        }
        else if (arg == "0" || arg.startsWith("s")) {
            mode = GameMode.SURVIVAL;
        }

        if (mode != null) {
            player.getBukkitPlayer().setGameMode(mode);
            player.sendMessage(
                player.getI18nMessage("gamemode.switched")
                    .replace("{mode}", mode.name())
            );
        } else {
            this.onBadUsage(ctx);
        }
    } 
}
