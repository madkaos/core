package com.madkaos.core.commands.player.friends;

import java.util.List;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.entities.PlayerData;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;

@Command(
    name = "list",
    minArguments = 1,
    arguments = {
        Argument.PLAYER
    }
)
public class FriendListSubCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        List<PlayerData> friends = player.getFriends();

        int count = friends.size();
        int limit = 10; // lol

        ComponentBuilder entries = new ComponentBuilder();
        
        for (PlayerData data : friends) {
            String username = data.displayName;
            entries.append(ChatColor.GRAY + username);
            entries.append(ChatColor.RED + " (offline)");
            entries.append("\n");
        }

        player.sendMessage(
            player.getI18nMessage("friends.list")
                .replace("{count}", count + "")
                .replace("{limit}", limit + "")
        );
        player.getBukkitPlayer().spigot().sendMessage(entries.create());
    }
}
