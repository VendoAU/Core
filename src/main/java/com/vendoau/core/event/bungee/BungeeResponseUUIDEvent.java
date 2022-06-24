package com.vendoau.core.event.bungee;

import net.minestom.server.entity.Player;

public class BungeeResponseUUIDEvent extends BungeeResponseEvent {

    private final String uuid;

    public BungeeResponseUUIDEvent(Player player, String uuid) {
        super(player);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
