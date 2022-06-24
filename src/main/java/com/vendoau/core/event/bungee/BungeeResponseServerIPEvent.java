package com.vendoau.core.event.bungee;

import net.minestom.server.entity.Player;

public class BungeeResponseServerIPEvent extends BungeeResponseEvent {

    private final String serverName;
    private final String ip;
    private final int port;

    public BungeeResponseServerIPEvent(Player player, String serverName, String ip, int port) {
        super(player);
        this.serverName = serverName;
        this.ip = ip;
        this.port = port;
    }

    public String getServerName() {
        return serverName;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
