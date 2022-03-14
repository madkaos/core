package com.madkaos.core.commands.player;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.messaging.packets.MessagePacket;
import com.madkaos.core.player.MadPlayer;

@Command(
    name = "reply",
    permission = "core.commands.reply",
    minArguments = 1,
    arguments = {
        Argument.LARGE_STRING
    }
)
public class ReplyCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadKaosCore plugin = ctx.getPlugin();
        MadPlayer player = ctx.getPlayer();
        String message = ctx.getArguments().getString(0);

        String last = plugin.getCacheEngine().getReplyTo(player.getData().displayName);

        if (last == null) {
            player.sendI18nMessage("reply.no-reply");
        } else {
            plugin.getMessageBroker().publish(
                new MessagePacket(player.getData().displayName, last, message)
            );

            player.sendMessage(
                player.getI18nMessage("message.your-message")
                    .replace("{player}", last)
                    .replace("{message}", message)
            );
        }
    }
}
