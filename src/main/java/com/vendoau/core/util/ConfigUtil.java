package com.vendoau.core.util;

import com.vendoau.core.config.BoundingBoxSerializer;
import com.vendoau.core.config.PosSerializer;
import com.vendoau.core.config.ServerSerializer;
import com.vendoau.core.server.Server;
import net.minestom.server.collision.BoundingBox;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.extensions.Extension;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public final class ConfigUtil {

    private ConfigUtil() {}

    public static HoconConfigurationLoader getLoader(Path path) {
        return HoconConfigurationLoader.builder()
                .path(path)
                .defaultOptions(options -> options.serializers(b -> b
                        .register(BoundingBox.class, BoundingBoxSerializer.INSTANCE)
                        .register(Pos.class, PosSerializer.INSTANCE)
                        .register(Server.class, ServerSerializer.INSTANCE)
                        .build()))
                .build();
    }

    public static HoconConfigurationLoader getLoader(URL url) {
        return HoconConfigurationLoader.builder()
                .url(url)
                .defaultOptions(options -> options.serializers(b -> b
                        .register(BoundingBox.class, BoundingBoxSerializer.INSTANCE)
                        .register(Pos.class, PosSerializer.INSTANCE)
                        .register(Server.class, ServerSerializer.INSTANCE)
                        .build()))
                .build();
    }

    public static HoconConfigurationLoader getLoader(Extension extension) {
        return getLoader(getExtensionConfigPath(extension));
    }

    public static HoconConfigurationLoader getLoader(Extension extension, String path) {
        return getLoader(extension.dataDirectory().resolve(path));
    }

    public static CommentedConfigurationNode getConfig(Path path) throws ConfigurateException {
        return getLoader(path).load();
    }

    public static CommentedConfigurationNode getConfig(URL url) throws ConfigurateException {
        return getLoader(url).load();
    }

    public static CommentedConfigurationNode getConfig(Extension extension) throws ConfigurateException {
        return getLoader(extension).load();
    }

    public static CommentedConfigurationNode getConfig(Extension extension, String path) throws ConfigurateException {
        return getLoader(extension, path).load();
    }

    public static void merge(HoconConfigurationLoader loader, CommentedConfigurationNode config, Extension extension, String resource) throws ConfigurateException {
        config.mergeFrom(ConfigUtil.getConfig(extension.getClass().getResource(resource)));
        loader.save(config);
    }

    public static boolean createNewConfig(Extension extension, File file) throws IOException {
        extension.dataDirectory().toFile().mkdir();
        return file.createNewFile();
    }

    public static boolean createNewConfig(Extension extension) throws IOException {
        return createNewConfig(extension, getExtensionConfigPath(extension).toFile());
    }

    public static Path getExtensionConfigPath(Extension extension) {
        return extension.dataDirectory().resolve(extension.descriptor().name().toLowerCase() + ".conf");
    }
}
