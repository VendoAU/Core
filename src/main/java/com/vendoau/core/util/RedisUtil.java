package com.vendoau.core.util;

import com.google.gson.JsonObject;
import com.vendoau.core.CoreExtension;
import com.vendoau.core.config.CoreConfig;
import net.minestom.server.MinecraftServer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.function.Consumer;

public final class RedisUtil {

    public RedisUtil() {}

    public static void publishPlayerCount(int count) {
        final JsonObject playerCountObject = new JsonObject();
        playerCountObject.addProperty("serverName", CoreExtension.getConfig().getServerName());
        playerCountObject.addProperty("playerCount", count);

        publish("PlayerCount", playerCountObject.toString());
    }

    public static void publishPlayerCount() {
        publishPlayerCount(MinecraftServer.getConnectionManager().getOnlinePlayers().size());
    }

    public static void publishServerStatus(boolean online) {
        final JsonObject serverStatusObject = new JsonObject();
        serverStatusObject.addProperty("serverName", CoreExtension.getConfig().getServerName());
        serverStatusObject.addProperty("online", online);

        publish("ServerStatus", serverStatusObject.toString());
    }

    public static void publish(String channel, String message) {
        final CoreConfig config = CoreExtension.getConfig();
        final String ip = config.getRedisIP();
        final int port = config.getRedisPort();
        final String password = config.getRedisPassword();

        try (final Jedis jedis = new Jedis(ip, port)) {
            jedis.auth(password);

            jedis.publish(channel, message);
        }
    }

    public static void subscribe(Consumer<String> message, String... channels) {
        final CoreConfig coreConfig = CoreExtension.getConfig();
        final String ip = coreConfig.getRedisIP();
        final int port = coreConfig.getRedisPort();
        final String password = coreConfig.getRedisPassword();

        new Thread(() -> {
            final Jedis jedis = new Jedis(ip, port);
            jedis.auth(password);

            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String s) {
                    message.accept(s);
                }
            }, channels);
        }).start();
    }
}
