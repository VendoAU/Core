package com.vendoau.core.util;

import net.minestom.server.MinecraftServer;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.DimensionType;

public final class InstanceUtil {

    private InstanceUtil() {}

    public static final DimensionType BRIGHT_DIMENSION;

    static {
        BRIGHT_DIMENSION = DimensionType.builder(NamespaceID.from("minecraft:bright"))
                .ambientLight(1)
                .fixedTime(6000L)
                .build();
        MinecraftServer.getDimensionTypeManager().addDimension(BRIGHT_DIMENSION);
    }
}
