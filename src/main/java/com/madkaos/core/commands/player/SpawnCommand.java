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
        player.getBukkitPlayer().teleport(loc);
        player.sendI18nMessage("spawn.teleport");
    }
}
