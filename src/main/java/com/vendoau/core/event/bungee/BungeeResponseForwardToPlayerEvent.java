package com.vendoau.core.event.bungee;

import net.minestom.server.entity.Player;

public class BungeeResponseForwardToPlayerEvent extends BungeeResponseEvent {

    private final String subChannel;
    private final short length;
    private final byte[] message;

    public BungeeResponseForwardToPlayerEvent(Player player, String subChannel, short length, byte[] message) {
        super(player);
        this.subChannel = subChannel;
        this.length = length;
        this.message = message;
    }

    public String getSubChannel() {
        return subChannel;
    }

    public short getLength() {
        return length;
    }

    public byte[] getMessage() {
        return message;
    }
}
