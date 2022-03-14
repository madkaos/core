package com.madkaos.core.commands.admin;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.messaging.packets.PlayerPunishPacket;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.PunishmentType;
import com.madkaos.core.player.entities.PlayerPunishment;
import com.madkaos.core.utils.TimeUtils;

@Command(
    name = "ban",
    permission = "core.commands.ban",
    minArguments = 1,
    arguments = { 
        Argument.PLAYER,
        Argument.LARGE_STRING
    }
)
public class BanCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        MadPlayer target = ctx.getArguments().getOfflinePlayer(0);
        String reasonRaw = ctx.getArguments().getString(1);

        System.out.println(target);

        if (reasonRaw != null) {
            String[] commandParts = reasonRaw.split(" ", 2);
            
            long time = -1;
            String reason = "";

            if (commandParts.length == 2) {
                time = TimeUtils.getMilisFromTimeArgument(commandParts[0]);
            }

            if (time == -1) {
                reason = commandParts[0];
            }

            if (commandParts.length == 2) {
                if (reason != "") {
                    reason += " ";
                }
                reason += commandParts[1];
            }

            PlayerPunishment punishment = new PlayerPunishment();
            punishment.createdOn = System.currentTimeMillis();
            punishment.expiresOn =  time;
            punishment.emisorId = player.getData().id;
            punishment.ofIP = false;
            punishment.playerId = target.getData().id;
            punishment.reason = reason;
            punishment.type = PunishmentType.BAN;
            punishment.save();

            PlayerPunishPacket packet = new PlayerPunishPacket(
                player.getData().displayName,
                target.getData().displayName,
                punishment
            );

            this.plugin.getMessageBroker().publish(packet);
        } else {
            System.out.println("la gui");
            // Open GUI
        }
    }
}
