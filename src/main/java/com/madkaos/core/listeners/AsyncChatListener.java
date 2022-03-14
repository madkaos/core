package com.madkaos.core.listeners;

import java.util.Iterator;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.PlayerFilter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatListener implements Listener {
    private MadKaosCore plugin;
    
    public AsyncChatListener(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        MadPlayer player = this.plugin.getPlayerManager().getPlayer(e.getPlayer());

        // Chat format
        e.setFormat(
            player.formatMessage(
                plugin.getMainConfig().getString("chat.format")
                    .replace("{message}", e.getMessage())
            )
        );
        
        // Visibility filter
        Iterator<Player> iterator = e.getRecipients().iterator();

        while(iterator.hasNext()) {
            Player bukkitRecipient = iterator.next();
            MadPlayer recipient = this.plugin.getPlayerManager().getPlayer(bukkitRecipient);
            int filter = recipient.getSettings().chatVisibilityFilter;

            if (recipient.getData().id.equals(player.getData().id)) {
                continue;
            } else if (filter == PlayerFilter.NOBODY) {
                iterator.remove();
            } else if (filter == PlayerFilter.ONLY_FRIENDS && !player.isFriend(recipient)) {
                iterator.remove();
            }
        }
    }
}
