package com.madkaos.core.commands.admin;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.modules.warp.WarpManager;
import com.madkaos.core.player.MadPlayer;

@Command(
    name = "delwarp",
    arguments = { Argument.STRING },
    permission = "core.delwarp",
    minArguments = 1
)
public class DelWarpCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        WarpManager warps = ctx.getPlugin().getWarpManager();
        String warpName = ctx.getArguments().getString(0);

        if (warps.getWarp(warpName) == null) {
            player.sendMessage(
                player.getI18nMessage("warp.not-exist")
                    .replace("{warp}", warpName)
            );
        } else {
            warps.deleteWarp(warpName);
            player.sendMessage(
                player.getI18nMessage("delwarp.deleted")
                    .replace("{warp}", warpName)
            );
        }
    } 
}
