package com.madkaos.core.listeners;

import java.util.Iterator;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.PlayerFilter;
import com.madkaos.core.player.entities.PlayerPunishment;
import com.madkaos.core.utils.ColorUtils;
import com.madkaos.core.utils.PunishmentsUtil;
import com.madkaos.core.utils.TimeUtils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatListener implements Listener {
    private MadKaosCore plugin;
    private int chatCooldown;
    
    public AsyncChatListener(MadKaosCore plugin) {
        this.plugin = plugin;

        this.chatCooldown =  this.plugin.getMainConfig().getInt("chat.message-cooldown");
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        MadPlayer player = this.plugin.getPlayerManager().getPlayer(e.getPlayer());

        // Chat cooldown
        if (player.getLastMessage() < this.chatCooldown) {
            player.sendI18nMessage("chat-cooldown");
            e.setCancelled(true);
            return;
        } else {
            player.setLastMessage();
        }

        // Check for mute
        PlayerPunishment mute = player.getActiveMute();
        if (mute != null) {
            player.sendMessage(
                player.getI18nMessage("mute.message")
                        .replace("{reason}", mute.reason)
                        .replace("{emisor}", mute.emisorName)
                        .replace("{expiration}", 
                            TimeUtils.getStringFromMilis(
                                PunishmentsUtil.getRemainingTime(mute.createdOn, mute.expiresOn)
                            )
                        )
                );
            e.setCancelled(true);
            return;
        }

        // Chat color
        if (player.hasPermission("core.chat-color")) {
            e.setMessage(ColorUtils.colorize(e.getMessage()));
        }

        // Chat format
        e.setFormat(
            player.formatMessage(
                plugin.getMainConfig().getString("chat.format")
            ).replace("{message}", "%s").replace("{username}", "%s")
        );

        // Visibility filter
        Iterator<Player> iterator = e.getRecipients().iterator();

        while(iterator.hasNext()) {
            Player bukkitRecipient = iterator.next();
            MadPlayer recipient = this.plugin.getPlayerManager().getPlayer(bukkitRecipient);
            int filter = recipient.getSettings().chatVisibilityFilter;

            if (recipient.getUUID().equals(player.getUUID())) {
                continue;
            } else if (filter == PlayerFilter.NOBODY) {
                iterator.remove();
            } else if (filter == PlayerFilter.ONLY_FRIENDS && !player.isFriend(recipient)) {
                iterator.remove();
            }
        }
    }
}
