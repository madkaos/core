package com.madkaos.core.gui.impl;

import com.madkaos.core.colors.Color;
import com.madkaos.core.colors.ColorMap;
import com.madkaos.core.gui.InventoryGUI;
import com.madkaos.core.gui.InventoryGUIContext;
import com.madkaos.core.gui.ItemBuilder;
import com.madkaos.core.player.MadPlayer;

public class ChatColorGUI extends InventoryGUI {
    private void setColor(InventoryGUIContext ctx, Color color) {
        MadPlayer player = ctx.getPlayer();

        player.sendMessage(
            player.getI18nMessage("chatcolor.toggle")
                .replace("{color}", color.getColoredDisplayName())
        );

        player.getSettings().chatColor = color.getCode();
        player.getSettings().save();
        player.getBukkitPlayer().closeInventory();
    }

    @Override
    public void init(InventoryGUIContext ctx) {
        for (Color color : ColorMap.COLORS) {
            ctx.addItem(
                new ItemBuilder("WOOL")
                    .setName(color.getColoredDisplayName())
                    .setData(color.getWoolData())
                    .setLeftClickAction(() -> { 
                        setColor(ctx, color); 
                    })
                );
        }
    }

    @Override
    public String getTitle(MadPlayer player) {
        return "Color del chat";
    }

    @Override
    public int getSize(MadPlayer player) {
        return 2 * 9;
    }
}
