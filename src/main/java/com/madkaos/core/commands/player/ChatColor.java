package com.madkaos.core.commands.player;

import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.gui.impl.ChatColorGUI;
import com.madkaos.core.player.MadPlayer;

@Command(
    name = "chatcolor",
    permission = "core.chatcolor"
)
public class ChatColor extends CommandListener {
    private ChatColorGUI gui;
    
    public ChatColor () {
        this.gui = new ChatColorGUI();
    }
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();
        gui.open(player);
    } 
}
