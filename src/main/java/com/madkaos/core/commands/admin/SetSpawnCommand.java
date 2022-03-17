package com.madkaos.core.commands.admin;

import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.Location;

@Command(name = "setspawn", permission = "core.setspawn")
public class SetSpawnCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getArguments().getPlayer(0);

        Location loc = player.getBukkitPlayer().getLocation();
        ctx.getPlugin().getMainConfig().setLocation("spawn.position", loc);
        player.sendI18nMessage("spawn.set");
    }
}
