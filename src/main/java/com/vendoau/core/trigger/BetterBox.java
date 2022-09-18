package com.vendoau.core.trigger;

import net.minestom.server.collision.BoundingBox;
import net.minestom.server.coordinate.Point;

public class BetterBox {

    private final Point src;
    private final BoundingBox boundingBox;

    public BetterBox(Point pos1, Point pos2) {
        src = pos1.add(pos2).div(2).withY(Math.min(pos1.y(), pos2.y()));

        final double width = Math.abs(pos1.x() - pos2.x());
        final double height = Math.abs(pos1.y() - pos2.y());
        final double depth = Math.abs(pos1.z() - pos2.z());
        boundingBox = new BoundingBox(width, height, depth);
    }

    public BetterBox(Point src, BoundingBox boundingBox) {
        this.src = src;
        this.boundingBox = boundingBox;
    }

    public Point src() {
        return src;
    }

    public BoundingBox boundingBox() {
        return boundingBox;
    }
}
