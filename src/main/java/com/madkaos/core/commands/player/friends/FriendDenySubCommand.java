package com.madkaos.core.commands.player.friends;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;

@Command(
    name = "deny",
    usageKey = "friends.deny.usage",
    minArguments = 1,
    arguments = {
        Argument.PLAYER
    }
)
public class FriendDenySubCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        MadPlayer target = ctx.getArguments().getOfflinePlayer(0);

        if (!player.getData().friendRequests.contains(target.getUUID())) {
            player.sendMessage(
                player.getI18nMessage("friends.deny.no-request")
                    .replace("{player}", target.getData().displayName)
            );
        }

        else {
            player.sendMessage(
                player.getI18nMessage("friends.deny.denied")
                    .replace("{player}", target.getData().displayName)
            );

            String targetId = target.getUUID();
            player.getData().friendRequests.remove(targetId);
            player.getData().save();
        }
    }
}
