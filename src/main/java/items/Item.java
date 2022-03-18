package com.madkaos.core.constructors;

import com.madkaos.core.MadKaosCore;
import com.madkaos.core.player.MadPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Item extends ItemStack {

    private final ItemStack itemStack;
    private final MadPlayer player;
    private final HashMap<Integer, String> command = new HashMap<>();

    public Item(MadPlayer player, Material material, String namePath, String lorePath){
        MadKaosCore plugin = MadKaosCore.getPlugin(MadKaosCore.class);
        List<String> itemLore = new ArrayList<>();
        this.player = player;
        itemStack = new ItemStack(material, 1, (byte) 3);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if(material.equals(Material.valueOf("SKULL_ITEM"))) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            skullMeta.setOwner(player.getBukkitPlayer().getName());
            itemStack.setItemMeta(skullMeta);
        }

        if (itemMeta == null) {
            return;
        }
        if (namePath!=null) {
            itemMeta.setDisplayName(player.formatMessage(plugin.getMainConfig().getString(namePath)));
        }
        if (lorePath!=null) {
            for (String line: plugin.getMainConfig().getStringList(lorePath)){
                itemLore.add(player.formatMessage(line));
            }
            itemMeta.setLore(itemLore);
        }

        itemStack.setItemMeta(itemMeta);
    }
    public void giveItems(int slot){
        player.getBukkitPlayer().getInventory().setItem(slot, itemStack);
    }
    public ItemStack getItemStack(){
        return itemStack;
    }
    public String getCommand(int slot) {
        return command.get(slot);
    }
    public void setCommand(String command, int slot) {
        this.command.put(slot, command);
    }
}
