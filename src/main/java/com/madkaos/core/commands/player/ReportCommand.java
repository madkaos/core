package com.madkaos.core.commands.player;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.messaging.packets.ReportPacket;
import com.madkaos.core.player.MadPlayer;

@Command(
    name = "report",
    minArguments = 2,
    arguments = {
        Argument.PLAYER,
        Argument.LARGE_STRING
    }
)
public class ReportCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadKaosCore plugin = ctx.getPlugin();
        MadPlayer player = ctx.getPlayer();
        MadPlayer target = ctx.getArguments().getOfflinePlayer(0);

        String author = player.getBukkitPlayer().getName();
        String reported = target.getData().displayName;
        String reason = ctx.getArguments().getString(1);

        ReportPacket packet = new ReportPacket(author, reported, reason);
        plugin.getMessageBroker().publish(packet);

        player.sendMessage(
            player.getI18nMessage("report.report-success")
                .replace("{player}", reported)
        );
    }
}
