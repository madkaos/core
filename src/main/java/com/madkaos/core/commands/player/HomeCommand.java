package com.madkaos.core.commands.player;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.entities.PlayerHome;

import org.bukkit.Bukkit;
import org.bukkit.Location;

@Command(name = "home", arguments = { Argument.STRING }, permission = "core.home", minArguments = 1, onlyGame = true)
public class HomeCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        String homeName = ctx.getArguments().getString(0);
        PlayerHome home = player.getHome(homeName);

        if (home == null) {
            player.sendMessage(
                    player.getI18nMessage("home.not-exist")
                            .replace("{homes}", player.getHomeAsString()));
        } else {
            Location loc = new Location(Bukkit.getWorld(home.world), home.x, home.y, home.z, (float) home.yaw,
                    (float) home.pitch);

            if (ctx.getPlugin().isLobby()) {
                // Instant teleport
                player.sendMessage(player.getI18nMessage("home.teleport").replace("{home}", home.name));
                player.teleport(loc);
            } else {
                // Delayed teleport
                player.sendMessage(player.getI18nMessage("home.teleport-after").replace("{home}", home.name));
                player.delayedTeleport(loc, player.getI18nMessage("home.teleport").replace("{home}", home.name));
            }
        }
    }
}
