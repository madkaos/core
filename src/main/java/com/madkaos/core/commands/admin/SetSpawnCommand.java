package com.madkaos.core.commands.admin;

import java.io.IOException;

import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.Location;

@Command(name = "setspawn", permission = "core.setspawn")
public class SetSpawnCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        Location loc = player.getBukkitPlayer().getLocation();
        ctx.getPlugin().getMainConfig().setLocation("spawn.position", loc);
        try {
            ctx.getPlugin().getMainConfig().save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendI18nMessage("spawn.set");
    }
}
