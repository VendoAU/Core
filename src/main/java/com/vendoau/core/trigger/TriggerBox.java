package com.vendoau.core.trigger;

import com.mattworzala.debug.shape.OutlineBox;
import com.mattworzala.debug.shape.Shape;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.extensions.Extension;

import java.awt.Color;

public class TriggerBox extends Trigger {

    private final BetterBox box;

    public TriggerBox(Extension extension, String name, BetterBox box) {
        super(extension, name);
        this.box = box;
    }

    @Override
    public boolean isInside(Player player) {
        return box.boundingBox().intersectEntity(box.src(), player);
    }

    @Override
    protected Shape getDebugShape() {
        return new OutlineBox.Builder()
                .start(Vec.fromPoint(box.pos1()))
                .end(Vec.fromPoint(box.pos2()))
                .color(new Color(255, 165, 0, 85).getRGB())
                .text(id)
                .build();
    }
}
