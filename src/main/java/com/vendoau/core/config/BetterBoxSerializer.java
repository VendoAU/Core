package com.vendoau.core.config;

import com.vendoau.core.trigger.BetterBox;
import com.vendoau.core.util.ConfigUtil;
import net.minestom.server.collision.BoundingBox;
import net.minestom.server.coordinate.Pos;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public final class BetterBoxSerializer implements TypeSerializer<BetterBox> {

    public static final BetterBoxSerializer INSTANCE = new BetterBoxSerializer();

    private BetterBoxSerializer() {}

    @Override
    public BetterBox deserialize(Type type, ConfigurationNode source) throws SerializationException {
        if (source.childrenMap().isEmpty()) {
            return deserializeShortVersion(source);
        }
        return deserializeLongVersion(source);
    }

    private BetterBox deserializeShortVersion(ConfigurationNode source) {
        final String[] split = source.getString().split(", ");

        if (split.length == 4) {
            final double x = Double.parseDouble(split[0]);
            final double y = Double.parseDouble(split[1]);
            final double z = Double.parseDouble(split[2]);
            final Pos src = new Pos(x, y, z);

            final double width = Double.parseDouble(split[3]);
            final double height = Double.parseDouble(split[4]);
            final double depth = Double.parseDouble(split[5]);
            final BoundingBox boundingBox = new BoundingBox(width, height, depth);

            return new BetterBox(src, boundingBox);
        }

        if (split.length == 6) {
            final double x1 = Double.parseDouble(split[0]);
            final double y1 = Double.parseDouble(split[1]);
            final double z1 = Double.parseDouble(split[2]);
            final Pos pos1 = new Pos(x1, y1, z1);

            final double x2 = Double.parseDouble(split[3]);
            final double y2 = Double.parseDouble(split[4]);
            final double z2 = Double.parseDouble(split[5]);
            final Pos pos2 = new Pos(x2, y2, z2);

            return new BetterBox(pos1, pos2);
        }

        throw new IllegalArgumentException();
    }

    private BetterBox deserializeLongVersion(ConfigurationNode source) throws SerializationException {
        if (ConfigUtil.hasNodes(source, "src", "width", "height", "depth")) {
            final Pos src = source.node("src").get(Pos.class);
            final double width = source.node("width").getDouble();
            final double height = source.node("height").getDouble();
            final double depth = source.node("depth").getDouble();
            return new BetterBox(src, new BoundingBox(width, height, depth));
        }

        if (ConfigUtil.hasNodes(source, "pos1", "pos2")) {
            final Pos pos1 = source.node("pos1").get(Pos.class);
            final Pos pos2 = source.node("pos2").get(Pos.class);
            if (pos1 != null && pos2 != null) {
                return new BetterBox(pos1, pos2);
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public void serialize(Type type, @Nullable BetterBox box, ConfigurationNode target) throws SerializationException {
        if (box == null) {
            target.set(null);
            return;
        }

        if (box.type() == BetterBox.Type.COORDINATES) {
            target.node("pos1").set(box.pos1());
            target.node("pos2").set(box.pos2());
            return;
        }

        target.node("src").set(box.src());
        target.node("width").set(box.boundingBox().width());
        target.node("height").set(box.boundingBox().height());
        target.node("depth").set(box.boundingBox().depth());
    }
}
