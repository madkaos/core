package com.madkaos.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import com.madkaos.core.player.MadPlayer;

public class ProxyUtils {
  public static void sendPlayerToServer(MadPlayer player, String server) {
    try {
      ByteArrayOutputStream b = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(b);
      out.writeUTF("Connect");
      out.writeUTF(server);
      player.sendPluginMessage("BungeeCord", b.toByteArray());
      b.close();
      out.close();
    } catch (Exception e) {
      player.sendMessage(
          player.getI18nMessage("server.unknown-error")
              .replace("{server}", server));
    }
  }
}
