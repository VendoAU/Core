package com.vendoau.core.util;

import com.vendoau.core.config.PosSerializer;
import com.vendoau.core.config.ServerSerializer;
import com.vendoau.core.server.Server;
import net.minestom.server.coordinate.Pos;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;

public class ConfigUtil {

    public static CommentedConfigurationNode getConfig(Path path) throws ConfigurateException {
        final HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
                .path(path)
                .defaultOptions(options -> options.serializers(builder -> {
                    builder.register(Pos.class, PosSerializer.INSTANCE)
                            .register(Server.class, ServerSerializer.INSTANCE)
                            .build();
                }))
                .build();
        return loader.load();
    }
}
