package com.madkaos.core.commands.player.friends;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.messaging.packets.PlayerRefreshPacket;
import com.madkaos.core.player.MadPlayer;

@Command(
    name = "remove",
    usageKey = "friends.remove.usage",
    minArguments = 1,
    arguments = {
        Argument.PLAYER
    }
)
public class FriendRemoveSubCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        MadPlayer target = ctx.getArguments().getOfflinePlayer(0);

        if (!target.getData().friends.contains(player.getData().id)) {
            player.sendMessage(
                player.formatMessage(
                    player.getI18nMessage("friends.remove.not-your-friend")
                        .replace("{player}", target.getData().displayName)
                )
            );
        }
        
        else {
            player.getData().friends.remove(target.getData().id);
            player.getData().save();

            target.getData().friends.remove(player.getData().id);
            target.getData().save();

            player.sendMessage(
                player.getI18nMessage("friends.remove.removed")
                    .replace("{player}", target.getData().displayName)
            );

            this.plugin.getMessageBroker().publishPlayerRefreshPacket(
                new PlayerRefreshPacket(target.getData().displayName)
            );
        }
    }
}
