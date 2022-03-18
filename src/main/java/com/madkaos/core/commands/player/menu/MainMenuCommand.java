package com.madkaos.core.commands.player.menu;

import com.madkaos.core.commands.Command;
import com.madkaos.core.commands.CommandContext;
import com.madkaos.core.commands.CommandListener;
import items.Item;
import com.madkaos.core.menus.Menu;
import com.madkaos.core.player.MadPlayer;
import org.bukkit.Material;

@Command(
        name = "menu",
        permission = "core.commands.menu",
        isVIP = false
)

public class MainMenuCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        MadPlayer player = ctx.getPlayer();

        Menu menu = new Menu(player, 54, "Menú principal");

        menu.addItem(20, new Item(player, Material.NAME_TAG, "menus.prefix.name", "menus.prefix.lore"));

        menu.addItem(45, new Item(player, Material.COMPASS, "menus.games-menu.name", "menus.games-menu.lore"));
        menu.addItem(53, new Item(player, Material.BARRIER, "menus.close.name", "menus.close.lore"));

        menu.addItem(2, new Item(player, Material.BOOK, "menus.aprendiz.name", "menus.aprendiz.lore"));
        menu.addItem(3, new Item(player, Material.BOOK, "menus.caballero.name", "menus.caballero.lore"));
        menu.addItem(4, new Item(player, Material.BOOK, "menus.rey.name", "menus.rey.lore"));
        menu.addItem(5, new Item(player, Material.BOOK, "menus.emperador.name", "menus.emperador.lore"));
        menu.addItem(6, new Item(player, Material.BOOK, "menus.leyenda.name", "menus.leyenda.lore"));

        menu.addItem(47, new Item(player, Material.PAPER, "menus.patreon.name", "menus.patreon.lore"));
        menu.addItem(48, new Item(player, Material.PAPER, "menus.discord.name", "menus.discord.lore"));
        menu.addItem(50, new Item(player, Material.PAPER, "menus.twitter.name", "menus.twitter.lore"));
        menu.addItem(51, new Item(player, Material.PAPER, "menus.invite-friend.name", "menus.invite-friend.lore"));
        menu.openMenu();
    }
}
