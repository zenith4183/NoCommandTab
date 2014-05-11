package com.minecats.nocommandtab;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.reflect.FieldAccessException;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Created by cindy on 5/4/14.
 */



public class NoCommandTab extends JavaPlugin {

    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        super.onEnable();

        protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(this,
                ConnectionSide.CLIENT_SIDE, ListenerPriority.NORMAL,
                new Integer[] { Integer.valueOf(203) }) {
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketID() == 203)
                    try {
                        PacketContainer packet = event.getPacket();
                        String message = (String) packet.getSpecificModifier(
                                String.class).read(0);
                        if ((message.startsWith("/")) && (!message.contains(" ")))
                            event.setCancelled(true);
                    } catch (FieldAccessException e) {
                        getLogger().log(Level.SEVERE, "Couldn't access field.", e);
                    }
            }

        });


        this.getLogger().info("NoCommandTab Loaded");
    }

    @Override
    public void onDisable() {
        super.onDisable();

        this.getLogger().info("NoCommandTab Disabled");
    }



}
