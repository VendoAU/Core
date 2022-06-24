package com.vendoau.core.event.bungee;

import net.minestom.server.entity.Player;

public class BungeeResponsePlayerCountEvent extends BungeeResponseEvent {

    private final String server;
    private final int playerCount;

    public BungeeResponsePlayerCountEvent(Player player, String server, int playerCount) {
        super(player);
        this.server = server;
        this.playerCount = playerCount;
    }

    public String getServer() {
        return server;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}
