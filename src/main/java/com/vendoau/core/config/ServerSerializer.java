package com.vendoau.core.config;

import com.vendoau.core.server.Server;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.net.InetSocketAddress;

public class ServerSerializer implements TypeSerializer<Server> {

    public static final ServerSerializer INSTANCE = new ServerSerializer();

    private ServerSerializer() {}

    @Override
    public Server deserialize(Type type, ConfigurationNode source) {
        final String name = source.node("name").getString();
        final String ip = source.node("ip").getString();
        final int port = source.node("port").getInt();
        final InetSocketAddress address = new InetSocketAddress(ip, port);

        return new Server(name, address);
    }

    @Override
    public void serialize(Type type, @Nullable Server server, ConfigurationNode target) throws SerializationException {
        target.node("name").set(server.getName());
        target.node("ip").set(server.getAddress().getHostName());
        target.node("port").set(server.getAddress().getAddress());
    }
}
