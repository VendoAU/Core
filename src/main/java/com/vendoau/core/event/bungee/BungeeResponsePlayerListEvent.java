package com.vendoau.core.event.bungee;

import net.minestom.server.entity.Player;

public class BungeeResponsePlayerListEvent extends BungeeResponseEvent {

    private final String server;
    private final String[] playerList;

    public BungeeResponsePlayerListEvent(Player player, String server, String[] playerList) {
        super(player);
        this.server = server;
        this.playerList = playerList;
    }

    public String getServer() {
        return server;
    }

    public String[] getPlayerList() {
        return playerList;
    }
}
