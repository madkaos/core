package com.madkaos.core.commands.player;

import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.config.Configuration;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.Location;

@Command(name = "spawn")
public class SpawnCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        Configuration config = ctx.getPlugin().getMainConfig();

        if (config.getBoolean("spawn.enabled")) {
            Location loc = config.getLocation("spawn.position", true);

            if (ctx.getPlugin().isLobby()) {
                // Instant teleport
                player.sendI18nMessage("spawn.teleport");
                player.teleport(loc);
            } else {
                // Delayed teleport
                player.sendI18nMessage("spawn.teleport-after");
                player.delayedTeleport(loc, player.getI18nMessage("spawn.teleport"));
            }
        } else {
            player.sendI18nMessage("spawn.not-exist");
        }
    }
}
