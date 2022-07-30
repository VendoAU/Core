package com.vendoau.core.util;

import net.minestom.server.coordinate.Pos;

public final class PosUtil {

    private PosUtil() {}

    public static Pos getPos(String s) {
        final String[] split = s.split(",");

        if (split.length != 3 && split.length != 5) {
            throw new IllegalArgumentException();
        }

        final double x = Double.parseDouble(split[0]);
        final double y = Double.parseDouble(split[1]);
        final double z = Double.parseDouble(split[2]);
        if (split.length != 5) return new Pos(x, y, z);

        final float yaw = Float.parseFloat(split[3]);
        final float pitch = Float.parseFloat(split[4]);
        return new Pos(x, y, z, yaw, pitch);
    }
}
