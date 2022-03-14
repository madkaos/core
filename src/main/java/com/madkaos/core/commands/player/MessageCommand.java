package com.madkaos.core.commands.player;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.messaging.packets.MessagePacket;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.PlayerFilter;

@Command(
    name = "message",
    permission = "core.commands.msg",
    minArguments = 2,
    arguments = {
        Argument.PLAYER,
        Argument.LARGE_STRING
    }
)
public class MessageCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadKaosCore plugin = ctx.getPlugin();
        MadPlayer player = ctx.getPlayer();
        MadPlayer target = ctx.getArguments().getOfflinePlayer(0);

        String username = target.getData().displayName;
        String message = ctx.getArguments().getString(1);

        if (player.getData().id.equals(target.getData().id)) {
            ctx.getPlayer().sendI18nMessage("message.cannot-your-self");
            return;
        }

        if (target.getSettings().messageRequestsFilter == PlayerFilter.NOBODY) {
            player.sendI18nMessage("message.user-dont-accept-message");
            return;
        } else if (target.getSettings().messageRequestsFilter == PlayerFilter.ONLY_FRIENDS) {
            if (!target.isFriend(player)) {
                player.sendI18nMessage("message.user-dont-accept-unknown");
                return;
            }
        }

        plugin.getCacheEngine().setReplyTo(player.getData().displayName, target.getData().displayName);
        plugin.getCacheEngine().setReplyTo(target.getData().displayName, player.getData().displayName);

        plugin.getMessageBroker().publish(
            new MessagePacket(player.getData().displayName, username, message)
        );

        player.sendMessage(
                player.getI18nMessage("message.your-message")
                    .replace("{player}", username)
                    .replace("{message}", message)
            );
    }
}
