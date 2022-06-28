package com.vendoau.core.util;

import com.google.gson.JsonObject;
import com.vendoau.core.CoreExtension;
import com.vendoau.core.config.CoreConfig;
import net.minestom.server.MinecraftServer;
import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static void publishPlayerCount() {
        final JsonObject playerCountObject = new JsonObject();
        playerCountObject.addProperty("serverName", CoreExtension.getConfig().getServerName());
        playerCountObject.addProperty("playerCount", MinecraftServer.getConnectionManager().getOnlinePlayers().size());

        publish("PlayerCount", playerCountObject.toString());
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
}
