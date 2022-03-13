package com.madkaos.core.messaging;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.messaging.packets.FriendRequestPacket;
import com.madkaos.core.messaging.packets.MessagePacket;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class MessageProcessor {
    private MadKaosCore plugin;
    
    public MessageProcessor(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    public void processMessagePacket(MessagePacket packet) {
        Player player = this.plugin.getServer().getPlayerExact(packet.getTarget());
        if (player.isOnline()) {
            player.sendMessage(packet.getMessage());
        }
    }

    public void processFriendRequestPacket(FriendRequestPacket packet) {
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
}
