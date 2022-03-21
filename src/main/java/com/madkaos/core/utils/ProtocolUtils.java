package com.madkaos.core.utils;

import java.lang.reflect.InvocationTargetException;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ProtocolUtils {
    public static void sendTabList(Player player, String header, String footer) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer pc = protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        StructureModifier<WrappedChatComponent> chatComponents = pc.getChatComponents();
        
        chatComponents.write(0, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', header)));
        chatComponents.write(1, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', footer)));
        
        try {
            protocolManager.sendServerPacket(player, pc);
        } catch (InvocationTargetException e) {
            // Ignored
        }
    }
}
