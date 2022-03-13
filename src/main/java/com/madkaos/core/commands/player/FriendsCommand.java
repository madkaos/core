package com.madkaos.core.commands.player;

import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.commands.player.friends.FriendAcceptSubCommand;
import com.madkaos.core.commands.player.friends.FriendAddSubCommand;
import com.madkaos.core.commands.player.friends.FriendListSubCommand;
import com.madkaos.core.commands.player.friends.FriendRequestsSubCommand;

@Command(
    name = "friends",
    permission = "core.commands.friends"
)
public class FriendsCommand extends CommandListener {
    public FriendsCommand() {
        this.addSubcommand(new FriendAcceptSubCommand());
        this.addSubcommand(new FriendAddSubCommand());
        this.addSubcommand(new FriendListSubCommand());
        this.addSubcommand(new FriendRequestsSubCommand());
    }

    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        ctx.getPlayer().sendI18nMessage("friends.help");
    } 
}
