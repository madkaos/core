package com.madkaos.core.commands.player.friends;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.PlayerFilter;

@Command(
    name = "add",
    usageKey = "friends.add.usage",
    minArguments = 1,
    arguments = {
        Argument.PLAYER
    }
)
public class FriendAddSubCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        MadPlayer target = ctx.getArguments().getOfflinePlayer(0);

        if (player.getData().id.equals(target.getData().id)) {
            ctx.getPlayer().sendI18nMessage("friends.add.cannot-your-self");
            return;
        }

        if (target.getData().friends.contains(player.getData().id)) {
            player.sendMessage(
                player.formatMessage(
                    player.getI18nMessage("friends.add.already-friend")
                        .replace("{friend_name}", target.getData().displayName)
                )
            );
        }
        
        else if (target.getData().friendRequests.contains(player.getData().id)) {
            player.sendI18nMessage("friends.add.already-sent");
        }

        else {
            if (target.getSettings().friendRequestsFilter == PlayerFilter.ANYBODY) {
                target.getData().friendRequests.add(player.getData().id);
                target.getData().save();
                player.sendI18nMessage("friends.add.request-sent");
            } else {
                player.sendI18nMessage("friends.add.user-dont-accept-friends");
            }
        }
    }
}
