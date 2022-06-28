package com.vendoau.core.util;

import com.vendoau.core.config.PosSerializer;
import com.vendoau.core.config.ServerSerializer;
import com.vendoau.core.server.Server;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.extensions.Extension;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.net.URL;
import java.nio.file.Path;

public class ConfigUtil {

    public static HoconConfigurationLoader getLoader(Path path) {
        return HoconConfigurationLoader.builder()
                .path(path)
                .defaultOptions(options -> options.serializers(builder -> {
                    builder.register(Pos.class, PosSerializer.INSTANCE)
                            .register(Server.class, ServerSerializer.INSTANCE)
                            .build();
                }))
                .build();
    }

    public static HoconConfigurationLoader getLoader(URL url) {
        return HoconConfigurationLoader.builder()
                .url(url)
                .defaultOptions(options -> options.serializers(builder -> {
                    builder.register(Pos.class, PosSerializer.INSTANCE)
                            .register(Server.class, ServerSerializer.INSTANCE)
                            .build();
                }))
                .build();
    }

    public static HoconConfigurationLoader getLoader(Extension extension) {
        return getLoader(extension.dataDirectory().resolve(extension.descriptor().name().toLowerCase() + ".conf"));
    }

    public static CommentedConfigurationNode getConfig(Path path) throws ConfigurateException {
        return getLoader(path).load();
    }

    public static CommentedConfigurationNode getConfig(URL url) throws ConfigurateException {
        return getLoader(url).load();
    }

    public static void merge(HoconConfigurationLoader loader, CommentedConfigurationNode config, Extension extension, String resource) throws ConfigurateException {
        config.mergeFrom(ConfigUtil.getConfig(extension.getClass().getResource(resource)));
        loader.save(config);
    }
}
