package com.madkaos.core.messaging;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.messaging.packets.FriendAcceptedPacket;
import com.madkaos.core.messaging.packets.FriendRequestPacket;
import com.madkaos.core.messaging.packets.MessagePacket;
import com.madkaos.core.messaging.packets.PlayerRefreshPacket;
import com.madkaos.core.messaging.packets.ReportPacket;
import com.madkaos.core.player.MadPlayer;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class MessageProcessor {
    private MadKaosCore plugin;
    
    public MessageProcessor(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    public void process(MessagePacket packet) {
        MadPlayer player = this.plugin.getPlayerManager().getPlayer(packet.getTarget());
        String author = packet.getAuthor();
        String message = packet.getMessage();
        if (player != null) {
            player.sendMessage(
                player.getI18nMessage("message.message-other")
                    .replace("{player}", author)
                    .replace("{message}", message)
            );
        }
    }

    public void process(FriendRequestPacket packet) {
        MadPlayer player = this.plugin.getPlayerManager().getPlayer(packet.getTarget());
        String author = packet.getAuthor();

        if (player != null) {
            player.getData().refresh();

            ComponentBuilder acceptMSG = new ComponentBuilder(
                player.formatMessage(
                    player.getI18nMessage("common.accept")
                )
            );
            BaseComponent[] acceptComponent = acceptMSG.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friends accept " + author)).create();
        
            ComponentBuilder denyMSG = new ComponentBuilder(
                player.formatMessage(
                    player.getI18nMessage("common.deny")
                )
            );
            BaseComponent[] denyComponent = denyMSG.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friends deny " + author)).create();
        
            ComponentBuilder actionMSG = new ComponentBuilder();
            actionMSG.append(
                player.formatMessage(
                    player.getI18nMessage("friends.add.friend-request")
                        .replace("{player}", packet.getAuthor())
                )
            );
            actionMSG.append(acceptComponent);
            actionMSG.append(" ");
            actionMSG.append(denyComponent);
            actionMSG.append("\n");
            actionMSG.append("\n");

            player.getBukkitPlayer().spigot().sendMessage(actionMSG.create());
        }
    }

    public void process(FriendAcceptedPacket packet) {
        MadPlayer player = this.plugin.getPlayerManager().getPlayer(packet.getTarget());
        if (player != null) {
            player.getData().refresh();
            player.sendMessage(
                player.getI18nMessage("friends.accept.accepted-notify")
                    .replace("{player}", packet.getAuthor())
            );
        }
    }

    public void process(PlayerRefreshPacket packet) {
        MadPlayer player = this.plugin.getPlayerManager().getPlayer(packet.getTarget());
        if (player != null) {
            player.getData().refresh();
        }
    }

    public void process(ReportPacket packet) {
        for (MadPlayer player : this.plugin.getPlayerManager().getPlayers()) {
            if (player.getBukkitPlayer().hasPermission("core.module.reports")) {
                ComponentBuilder builder = new ComponentBuilder(
                    player.formatMessage(
                        player.getI18nMessage("report.report-notify")
                            .replace("{author}", packet.getAuthor())
                            .replace("{target}", packet.getTarget())
                            .replace("{reason}", packet.getReason())
                    )
                );

                builder.append(
                    new ComponentBuilder("§6§nClick para ir al servidor")
                        .event(
                            new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/jump " + packet.getTarget())
                    ).create()
                );

                builder.append("\n");

                player.getBukkitPlayer().spigot().sendMessage(builder.create());
            }
        }
    }
}