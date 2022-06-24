package com.vendoau.core.event.bungee;

import net.minestom.server.entity.Player;

public class BungeeResponseUUIDOtherEvent extends BungeeResponseEvent {

    private final String playerName;
    private final String uuid;

    public BungeeResponseUUIDOtherEvent(Player player, String playerName, String uuid) {
        super(player);
        this.playerName = playerName;
        this.uuid = uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getUuid() {
        return uuid;
    }
}
