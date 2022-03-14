package com.madkaos.core.commands.admin;

import com.madkaos.core.commands.Argument;
import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.entities.PlayerData;

import org.bukkit.ChatColor;

import net.md_5.bungee.api.chat.ComponentBuilder;

@Command(
    name = "alts",
    permission = "core.commands.alts",
    minArguments = 1,
    arguments = { 
        Argument.PLAYER
    }
)
public class AltsCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        MadPlayer target = ctx.getArguments().getOfflinePlayer(0);
        PlayerData[] alts = target.getAlts();

        ComponentBuilder entries = new ComponentBuilder();
        for (PlayerData data : alts) {
            String username = data.displayName;
            entries.append(ChatColor.GRAY + username);
            entries.append(ChatColor.DARK_GRAY + " (offline)");
            entries.append("\n");
        }

        player.sendMessage(
            player.getI18nMessage("alts.alts-list")
                .replace("{player}", target.getData().displayName)
        );
        player.getBukkitPlayer().spigot().sendMessage(entries.create());
    }
}
