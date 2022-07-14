package com.vendoau.core.util;

import com.vendoau.core.CoreExtension;
import net.minestom.server.collision.BoundingBox;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class TriggerBox {

    private final List<Player> players = new ArrayList<>();

    public TriggerBox(Point src, BoundingBox boundingBox) {
        CoreExtension.get().eventNode().addListener(PlayerMoveEvent.class, event -> {
            final Player player = event.getPlayer();

            if (boundingBox.intersectEntity(src, player)) {
                players.add(player);
                onEnter(player);
            } else if (players.contains(player)) {
                players.remove(player);
                onExit(player);
            }
        });
    }

    protected abstract void onEnter(Player player);

    protected abstract void onExit(Player player);
}
