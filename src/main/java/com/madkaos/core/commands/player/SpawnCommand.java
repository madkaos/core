package com.madkaos.core.commands.player;

import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.Location;

@Command(name = "spawn")
public class SpawnCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();

        Location loc = ctx.getPlugin().getMainConfig().getLocation("spawn.position", true);

        if (ctx.getPlugin().isLobby()) {
            player.sendI18nMessage("spawn.teleport");
            player.teleport(loc);
        } else {
            player.sendI18nMessage("spawn.teleport-after");
            player.teleport(loc, 3, player.getI18nMessage("spawn.teleport"));
        }
    }
}
