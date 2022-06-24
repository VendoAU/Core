package com.vendoau.core.event.bungee;

import net.minestom.server.entity.Player;

public class BungeeResponseGetServerEvent extends BungeeResponseEvent {

    private final String server;

    public BungeeResponseGetServerEvent(Player player, String server) {
        super(player);
        this.server = server;
    }

    public String getServer() {
        return server;
    }
}
