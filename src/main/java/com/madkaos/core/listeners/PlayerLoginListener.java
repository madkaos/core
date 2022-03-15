package com.madkaos.core.listeners;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.entities.PlayerPunishment;
import com.madkaos.core.utils.PunishmentsUtil;
import com.madkaos.core.utils.TimeUtils;

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
            // Download initial player data
            MadPlayer player = this.plugin.getPlayerManager().addPlayer(e.getPlayer());
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
                return;
            }
        }
    }
}
