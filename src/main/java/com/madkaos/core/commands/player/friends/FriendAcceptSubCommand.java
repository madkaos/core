package com.madkaos.core.commands.player.friends;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.messaging.packets.FriendAcceptedPacket;
import com.madkaos.core.player.MadPlayer;

@Command(
    name = "accept",
    usageKey = "friends.accept.usage",
    minArguments = 1,
    arguments = {
        Argument.PLAYER
    }
)
public class FriendAcceptSubCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        MadPlayer target = ctx.getArguments().getOfflinePlayer(0);

        int count = player.getData().friends.size();
        int max = 10;

        if (!player.getData().friendRequests.contains(target.getData().id)) {
            player.sendMessage(
                player.getI18nMessage("friends.accept.no-request")
                    .replace("{friend_name}", target.getData().displayName)
            );
        }

        else if (count >= max) {
            player.sendMessage(
                player.getI18nMessage("friends.accept.max-friends-reached")
                    .replace("{max}", max + "")
                    .replace("{count}", count + "")
            );
        }

        else if (target.getData().friendRequests.size() >= max) {
            player.sendMessage(
                player.getI18nMessage("friends.accept.max-friends-other")
                    .replace("{player}", target.getData().displayName)
            );
        }

        else {
            player.sendMessage(
                player.getI18nMessage("friends.accept.accepted")
                    .replace("{player}", target.getData().displayName)
            );

            String playerId = player.getData().id;
            String targetId = target.getData().id;

            target.getData().friends.add(playerId);
            target.getData().save();

            player.getData().friends.add(targetId);
            player.getData().friendRequests.remove(targetId);
            player.getData().save();

            this.plugin.getMessageBroker().publishFriendAcceptPacket(
                    new FriendAcceptedPacket(
                        player.getData().displayName,
                        target.getData().displayName
                    )
                );
        }
    }
}
