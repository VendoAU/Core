package com.vendoau.core.event.bungee;

import net.minestom.server.entity.Player;

public class BungeeResponseIPEvent extends BungeeResponseEvent {

    private final String ip;
    private final int port;

    public BungeeResponseIPEvent(Player player, String ip, int port) {
        super(player);
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
