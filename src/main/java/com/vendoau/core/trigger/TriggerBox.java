package com.vendoau.core.trigger;

import com.mattworzala.debug.shape.Box;
import com.mattworzala.debug.shape.Shape;
import net.minestom.server.collision.BoundingBox;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.extensions.Extension;

import java.awt.Color;

public abstract class TriggerBox extends Trigger {

    private final Point src;
    private final BoundingBox boundingBox;

    public TriggerBox(Extension extension, String name, Point src, BoundingBox boundingBox) {
        super(extension, name);
        this.src = src;
        this.boundingBox = boundingBox;
    }

    @Override
    protected boolean isInside(Player player) {
        return boundingBox.intersectEntity(src, player);
    }

    @Override
    public Shape getDebugShape() {
        return new Box.Builder()
                .start(Vec.fromPoint(src.add(boundingBox.relativeStart())))
                .end(Vec.fromPoint(src.add(boundingBox.relativeEnd())))
                .color(new Color(255, 165, 0, 85).getRGB())
                .build();
    }
}
