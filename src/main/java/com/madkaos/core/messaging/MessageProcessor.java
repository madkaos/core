package com.madkaos.core.messaging;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.messaging.packets.FriendRequestPacket;
import com.madkaos.core.messaging.packets.MessagePacket;
import com.madkaos.core.player.MadPlayer;

import org.bukkit.entity.Player;

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

        if (player != null) {
            player.getData().refresh();
        
            player.sendMessage(
                player.getI18nMessage("friends.add.friend-request")
                    .replace("{player}", packet.getAuthor())
            );
        }
    }
}
