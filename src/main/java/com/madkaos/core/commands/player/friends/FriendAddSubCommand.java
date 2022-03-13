package com.madkaos.core.commands.player.friends;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.OfflineMadPlayer;
import com.madkaos.core.player.PlayerFilter;
import com.madkaos.core.utils.ArrayUtils;

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
        OfflineMadPlayer target = ctx.getArguments().getOfflinePlayer(0);

        if (player.getData().id.equals(target.getData().id)) {
            ctx.getPlayer().sendI18nMessage("friends.add.cannot-your-self");
            return;
        }

        if (ArrayUtils.contains(target.getData().friends, player.getData().id)) {
            player.sendMessage(
                player.formatMessage(
                    player.getI18nMessage("friends.add.already-friend")
                        .replace("{friend_name}", target.getData().displayName)
                )
            );
        }
        
        else if (ArrayUtils.contains(target.getData().friendRequests, player.getData().id)) {
            player.sendI18nMessage("friends.add.already-sent");
        }

        else {
            target.downloadSettings();

            if (target.getSettings().friendRequestsFilter == PlayerFilter.ANYBODY) {
                ArrayUtils.addElement(target.getData().friendRequests, player.getData().id);
                player.sendI18nMessage("friends.add.request-sent");
            } else {
                player.sendI18nMessage("friends.add.user-dont-accept-friends");
            }
        }
    }
}
