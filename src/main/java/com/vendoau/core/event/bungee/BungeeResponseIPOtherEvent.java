package com.vendoau.core.event.bungee;

import net.minestom.server.entity.Player;

public class BungeeResponseIPOtherEvent extends BungeeResponseEvent {

    private final String username;
    private final String address;
    private final int port;

    public BungeeResponseIPOtherEvent(Player player, String username, String address, int port) {
        super(player);
        this.username = username;
        this.address = address;
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
