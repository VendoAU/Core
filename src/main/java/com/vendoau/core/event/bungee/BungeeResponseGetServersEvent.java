package com.vendoau.core.event.bungee;

import net.minestom.server.entity.Player;

public class BungeeResponseGetServersEvent extends BungeeResponseEvent {

    private final String[] servers;

    public BungeeResponseGetServersEvent(Player player, String[] servers) {
        super(player);
        this.servers = servers;
    }

    public String[] getServers() {
        return servers;
    }
}
