package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.entities.PlayerPunishment;
import com.madkaos.core.utils.PunishmentsUtil;
import com.madkaos.core.utils.TimeUtils;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerLoginListener implements Listener {
    private MadKaosCore plugin;
    
    public PlayerLoginListener(MadKaosCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        if (e.getResult() == Result.ALLOWED) {
            // Initialize player
            MadPlayer player = this.plugin.getPlayerManager().addPlayer(e.getPlayer());

            // Check for server full
            int onlinePlayers = Bukkit.getOnlinePlayers().size();
            int maxPlayers = this.plugin.getMainConfig().getInt("slots.normal");
            int maxVipPlayers = this.plugin.getMainConfig().getInt("slots.vip");

            if (onlinePlayers >= maxPlayers) {
                if (!player.hasPermission("core.slot.bypass")) {
                    if (!player.hasPermission("core.slot.vip")) {
                        e.disallow(
                            Result.KICK_FULL, 
                            player.formatMessage(player.getI18nMessage("slots.server-full"))
                        );
                        player.free();
                        return;
                    }

                    else {
                        if (onlinePlayers >= maxVipPlayers) {
                            e.disallow(
                                Result.KICK_FULL, 
                                player.formatMessage(player.getI18nMessage("slots.server-full-vip"))
                            );
                            player.free();
                            return;
                        }
                    }
                }
            }

            // Download initial player data
            player.downloadData();
            player.downloadSettings();
            player.downloadPunishments();

            // Check for ban
            PlayerPunishment ban = player.getActiveBan();
            if (ban != null) {
                String emisorName = ban.emisorName;

                String expires = ban.expiresOn == -1 
                    ? 
                    "(Permanente)" 
                    : 
                    TimeUtils.getStringFromMilis(PunishmentsUtil.getRemainingTime(ban.createdOn, ban.expiresOn));

                e.disallow(
                    Result.KICK_BANNED, 
                    player.formatMessage(
                        player.getI18nMessage("ban.message")
                            .replace("{emisor}", emisorName)
                            .replace("{reason}", ban.reason)
                            .replace("{expiration}", expires)
                    )
                );
                player.free();
                return;
            }
        }
    }
}
