package com.madkaos.core.commands.player.friends;

import java.util.List;

import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.entities.PlayerData;

@Command(
    name = "list"
)
public class FriendListSubCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        List<PlayerData> friends = player.getFriends();

        int count = friends.size();
        int limit = 10; // lol

        String entries = "";
        for (PlayerData data : friends) {
            if (entries != "") {
                entries += "\n";
            }

            entries += "&7" + data.displayName + " &8(&7offline&8)";
        }

        player.sendMessage(
            player.getI18nMessage("friends.list")
                .replace("{count}", count + "")
                .replace("{limit}", limit + "")
                .replace("{friends}", entries)
        );
    }
}
