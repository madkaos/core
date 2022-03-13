package com.madkaos.core.commands.player.friends;

import java.util.List;

import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.entities.PlayerData;

import org.bukkit.ChatColor;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

@Command(
    name = "requests"
)
public class FriendRequestsSubCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        List<PlayerData> requests = player.getFriendRequests();

        int count = requests.size();

        ComponentBuilder entries = new ComponentBuilder();

        for (PlayerData data : requests) {
            String username = data.displayName;

            entries.append(ChatColor.GRAY + username);
            entries.append(" ");
            entries.append(new ComponentBuilder(
                player.formatMessage(
                    player.getI18nMessage("common.accept-icon")
                )
            ).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friends accept " + username)).create());
            entries.append(" ");
            entries.append(new ComponentBuilder(
                player.formatMessage(
                    player.getI18nMessage("common.deny-icon")
                )
            ).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friends accept " + username)).create());
            entries.append("\n");
        }

        player.sendMessage(
            player.getI18nMessage("friends.requests")
                .replace("{count}", count + "")
        );
        player.getBukkitPlayer().spigot().sendMessage(entries.create());
    }
}
