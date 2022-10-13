package com.vendoau.core.trigger;

import com.mattworzala.debug.shape.OutlineBox;
import com.mattworzala.debug.shape.Shape;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.extensions.Extension;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TriggerBox extends Trigger {

    private final List<BetterBox> boxes;

    public TriggerBox(Extension extension, String name, BetterBox box) {
        super(extension, name);
        this.boxes = Collections.singletonList(box);
    }

    public TriggerBox(Extension extension, String name, List<BetterBox> boxes) {
        super(extension, name);
        this.boxes = boxes;
    }

    @Override
    public boolean isInside(Player player) {
        for (BetterBox box : boxes) {
            if (box.boundingBox().intersectEntity(box.src(), player)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected List<Shape> getDebugShapes() {
        final List<Shape> shapes = new ArrayList<>();
        for (BetterBox box : boxes) {
            shapes.add(new OutlineBox.Builder()
                    .start(Vec.fromPoint(box.pos1()))
                    .end(Vec.fromPoint(box.pos2()))
                    .color(new Color(255, 165, 0, 85).getRGB())
                    .text(id)
                    .build());
        }
        return shapes;
    }
}
