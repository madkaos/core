package com.madkaos.core.tasks;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;
import com.madkaos.core.player.PendingTeleport;

public class PendingTeleportTask implements Runnable {
    private MadKaosCore plugin;

    public PendingTeleportTask(MadKaosCore plugin) {
        this.plugin = plugin;
    }

	@Override
	public void run() {
		for (MadPlayer player : this.plugin.getPlayerManager().getPlayers()) {
            PendingTeleport teleport = player.getPendingTeleport();
            if (teleport != null) {
                teleport.tick();
                if (teleport.isHandled()) {
                    player.cancelPendingTeleport();
                }
            }
        }
	}
    
}
