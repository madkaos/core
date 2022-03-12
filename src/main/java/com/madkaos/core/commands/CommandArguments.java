package com.madkaos.core.commands;

import java.util.ArrayList;
import java.util.List;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.errors.BadArgumentException;
import com.madkaos.core.errors.PlayerNotFoundException;
import com.madkaos.core.errors.PlayerOfflineException;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.OfflineMadPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandArguments {
    private MadKaosCore plugin;
    private Argument[] requiredArguments;
    
    private List<Object> arguments = new ArrayList<>();

    public CommandArguments(MadKaosCore plugin, Argument[] requiredArguments) {
        this.plugin = plugin;
        this.requiredArguments = requiredArguments;
    }

    public String getString(int index) {
        return (String) this.arguments.get(index);
    }

    public int getInt(int index) {
        return (int) this.arguments.get(index);
    }

    public boolean getBoolean(int index) {
        return (boolean) this.arguments.get(index);
    }

    public OfflineMadPlayer getOfflinePlayer(int index) {
        return (OfflineMadPlayer) this.arguments.get(index);
    }

    public MadPlayer getPlayer(int index) {
        return (MadPlayer) this.arguments.get(index);
    }

    public void parse(String[] args) throws BadArgumentException, PlayerNotFoundException, PlayerOfflineException {
        int i = 0;

        for (String arg : args) {
            if (this.requiredArguments.length <= i) {
                this.arguments.add(arg);
                continue;
            }

            Argument type = this.requiredArguments[i];
            Object value = null;

            if (type == Argument.BOOL) {
                if (arg.equalsIgnoreCase("true")) {
                    value = true;
                } else if (arg.equalsIgnoreCase("false")) {
                    value = false;
                }
                throw new BadArgumentException(arg, "true o false");
            }

            if (type == Argument.INT) {
                try {
                    value = Integer.parseInt(arg);
                } catch (Exception _exception) {
                    throw new BadArgumentException(arg, "un número");
                }
            }

            if (type == Argument.ONLINE_PLAYER) {
                Player player = Bukkit.getServer().getPlayer(arg);
                if (player.isOnline()) {
                    value = this.plugin.getPlayerManager().getPlayer(player);
                } else {
                    throw new PlayerOfflineException(arg);
                }
            }

            if (type == Argument.PLAYER) {
                OfflineMadPlayer player = new OfflineMadPlayer(plugin, arg);
                player.downloadData();
                if (player.exist()) {
                    value = player;
                } else {
                    throw new PlayerNotFoundException(arg);
                }
            }

            if (type == Argument.STRING) {
                value = arg;
            }

            i++;
            this.arguments.add(value);
        }
    }
}
