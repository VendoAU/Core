package com.vendoau.core.config;

import net.minestom.server.coordinate.Pos;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public final class PosSerializer implements TypeSerializer<Pos> {

    public static final PosSerializer INSTANCE = new PosSerializer();

    private PosSerializer() {}

    @Override
    public Pos deserialize(Type type, ConfigurationNode source) {
        final String[] split = source.getString().split(",");

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

    @Override
    public void serialize(Type type, @Nullable Pos pos, ConfigurationNode target) throws SerializationException {
        if (pos == null) {
            target.raw(null);
            return;
        }

        final String[] split = new String[5];
        split[0] = String.valueOf(pos.x());
        split[1] = String.valueOf(pos.y());
        split[2] = String.valueOf(pos.z());
        split[3] = String.valueOf(pos.yaw());
        split[4] = String.valueOf(pos.pitch());

        target.set(String.join(", ", split));
    }
}
