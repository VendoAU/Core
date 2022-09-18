package com.vendoau.core.trigger;

import com.mattworzala.debug.DebugMessage;
import com.mattworzala.debug.shape.Shape;
import com.vendoau.core.CoreExtension;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.extensions.Extension;
import net.minestom.server.utils.NamespaceID;

import java.util.ArrayList;
import java.util.List;

public abstract class Trigger {

    private final NamespaceID id;
    protected final List<Player> players = new ArrayList<>();

    public Trigger(Extension extension, String name) {
        id = NamespaceID.from(name);

        extension.eventNode().addListener(PlayerMoveEvent.class, event -> {
            final Player player = event.getPlayer();

            final boolean inList = players.contains(player);
            final boolean inTrigger = isInside(player);
            if (!inList && inTrigger) {
                players.add(player);
                onEnter(player);
            } else if (inList && !inTrigger) {
                players.remove(player);
                onExit(player);
            }
        });

        CoreExtension.getTriggerManager().add(this);
    }

    protected abstract boolean isInside(Player player);

    protected abstract void onEnter(Player player);

    protected abstract void onExit(Player player);

    public abstract Shape getDebugShape();

    public final void showDebug(Player player) {
        final DebugMessage debugMessage = DebugMessage.builder()
                .set(id, getDebugShape())
                .build();
        player.sendPacket(debugMessage.getPacket());
    }

    public final void hideDebug(Player player) {
        final DebugMessage debugMessage = DebugMessage.builder()
                .remove(id)
                .build();
        player.sendPacket(debugMessage.getPacket());
    }
}
