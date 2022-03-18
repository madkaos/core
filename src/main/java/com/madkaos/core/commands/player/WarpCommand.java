package com.madkaos.core.commands.player;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.modules.warp.Warp;
import com.madkaos.core.player.MadPlayer;

@Command(
    name = "warp",
    arguments = { Argument.STRING },
    permission = "core.warp"
)
public class WarpCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        String warpName = ctx.getArguments().getString(0);
        
        if (warpName == null) {
            player.sendMessage(
                player.getI18nMessage("warp.message")
                    .replace("{warps}", ctx.getPlugin().getWarpManager().getWarpsAsString())
            );
        } else {
            Warp warp = ctx.getPlugin().getWarpManager().getWarp(warpName);

            if (warp != null) {
                player.getBukkitPlayer().teleport(warp.getLocation());
                player.sendMessage(
                    player.getI18nMessage("warp.teleported")
                        .replace("{warp}", warp.getName())
                );
            } else {
                player.sendMessage(
                    player.getI18nMessage("warp.not-exist")
                        .replace("{warp}", warpName)
                );
            }
        }
    } 
}
