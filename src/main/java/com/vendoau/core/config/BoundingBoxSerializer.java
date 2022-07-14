package com.vendoau.core.config;

import net.minestom.server.collision.BoundingBox;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public final class BoundingBoxSerializer implements TypeSerializer<BoundingBox> {

    public static final BoundingBoxSerializer INSTANCE = new BoundingBoxSerializer();

    private BoundingBoxSerializer() {}

    @Override
    public BoundingBox deserialize(Type type, ConfigurationNode source) {
        if (source.childrenMap().isEmpty()) {
            return deserializeShortVersion(source);
        }
        return deserializeLongVersion(source);
    }

    private BoundingBox deserializeShortVersion(ConfigurationNode source) {
        final String[] split = source.getString().split(",");

        if (split.length != 3) {
            throw new IllegalArgumentException();
        }

        final double width = Double.parseDouble(split[0]);
        final double height = Double.parseDouble(split[1]);
        final double depth = Double.parseDouble(split[2]);
        return new BoundingBox(width, height, depth);
    }

    private BoundingBox deserializeLongVersion(ConfigurationNode source) {
        final double width = source.node("width").getDouble();
        final double height = source.node("height").getDouble();
        final double depth = source.node("depth").getDouble();
        return new BoundingBox(width, height, depth);
    }

    @Override
    public void serialize(Type type, @Nullable BoundingBox boundingBox, ConfigurationNode target) throws SerializationException {
        if (boundingBox == null) {
            target.set(null);
            return;
        }

        target.node("width").set(boundingBox.width());
        target.node("height").set(boundingBox.height());
        target.node("depth").set(boundingBox.depth());
    }
}
