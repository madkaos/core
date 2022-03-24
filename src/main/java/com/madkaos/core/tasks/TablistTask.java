package com.madkaos.core.tasks;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.utils.ProtocolUtils;

public class TablistTask implements Runnable {
    private MadKaosCore plugin;

    public TablistTask(MadKaosCore plugin) {
        this.plugin = plugin;
    }

	@Override
	public void run() {
        if (this.plugin.getMainConfig().getBoolean("tablist.enabled")) {
            for (MadPlayer player : this.plugin.getPlayerManager().getPlayers()) {
                String header = player.formatMessage(plugin.getMainConfig().getString("tablist.header"));
                String footer = player.formatMessage(plugin.getMainConfig().getString("tablist.footer"));

                ProtocolUtils.sendTabList(player.getBukkitPlayer(), header, footer);
            }
        }
	}
    
}
