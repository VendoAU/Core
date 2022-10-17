package com.vendoau.core.util;

import com.mattworzala.debug.shape.OutlineBox;
import com.vendoau.core.trigger.BetterBox;
import net.minestom.server.coordinate.Vec;

public final class DebugUtil {

    private DebugUtil() {}

    public static OutlineBox.Builder createOutlineBox(BetterBox betterBox) {
        return new OutlineBox.Builder()
                .start(Vec.fromPoint(betterBox.pos1().sub(1)))
                .end(Vec.fromPoint(betterBox.pos2()));
    }
}
