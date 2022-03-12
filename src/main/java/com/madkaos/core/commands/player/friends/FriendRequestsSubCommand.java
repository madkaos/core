package com.madkaos.core.commands.player.friends;

import java.util.List;

import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.entities.PlayerData;

@Command(
    name = "requests"
)
public class FriendRequestsSubCommand extends CommandListener {
    @Override
    protected void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        List<PlayerData> requests = player.getFriendRequests();

        int count = requests.size();

        String entries = "";
        for (PlayerData data : requests) {
            if (entries != "") {
                entries += "\n";
            }

            entries += "&7" + data.displayName + " &8(&7offline&8)";
        }

        player.sendMessage(
            player.getI18nMessage("friends.requests")
                .replace("{count}", count + "")
                .replace("{requests}", entries)
        );
    }
}
