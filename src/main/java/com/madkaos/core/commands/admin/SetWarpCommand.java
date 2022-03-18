package com.madkaos.core.commands.admin;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.modules.warp.WarpManager;
import com.madkaos.core.player.MadPlayer;

@Command(
    name = "setwarp",
    arguments = { Argument.STRING },
    permission = "core.setwarp",
    minArguments = 1
)
public class SetWarpCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        WarpManager warps = ctx.getPlugin().getWarpManager();
        String warpName = ctx.getArguments().getString(0);

        if (warps.getWarp(warpName) == null) {
            warps.createWarp(warpName, player.getBukkitPlayer().getLocation());
            player.sendMessage(
                player.getI18nMessage("setwarp.created")
                    .replace("{warp}", warpName)
            );
        } else {
            player.sendI18nMessage("setwarp.already-exist");
        }
    } 
}
