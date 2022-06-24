package com.vendoau.core.event.bungee;

import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public abstract class BungeeResponseEvent implements PlayerEvent {

    private final Player player;

    public BungeeResponseEvent(Player player) {
        this.player = player;
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }
}
