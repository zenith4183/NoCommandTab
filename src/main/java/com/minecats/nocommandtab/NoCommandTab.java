package com.minecats.nocommandtab;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public class NoCommandTab extends JavaPlugin {
  private ProtocolManager protocolManager;

  public void onEnable() {
    super.onEnable();

    this.protocolManager = ProtocolLibrary.getProtocolManager();
    this.protocolManager.addPacketListener(new PacketAdapter(this,
      ListenerPriority.NORMAL,
      PacketType.Play.Client.TAB_COMPLETE) {
      @Override
      public void onPacketReceiving(PacketEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE)
          try {
            PacketContainer packet = event.getPacket();
            String message = 
              packet.getSpecificModifier(
              String.class).read(0);
            if ((message.startsWith("/")) && (!message.contains(" ")))
              event.setCancelled(true);
          } catch (FieldAccessException e) {
            NoCommandTab.this.getLogger().log(Level.SEVERE, "Couldn't access field.", e);
          }
      }
    });
    getLogger().info("NoCommandTab Loaded");
  }

  public void onDisable() {
    super.onDisable();

    getLogger().info("NoCommandTab Disabled");
  }
}