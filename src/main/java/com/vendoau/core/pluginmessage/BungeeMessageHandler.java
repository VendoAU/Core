package com.vendoau.core.pluginmessage;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerPluginMessageEvent;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.CompletableFuture;

// Inspired by https://github.com/leonardosnt/BungeeChannelApi
public class BungeeMessageHandler {

    private final Map<String, Queue<CompletableFuture<?>>> callbackMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public BungeeMessageHandler() {
        MinecraftServer.getGlobalEventHandler().addListener(PlayerPluginMessageEvent.class, event -> {
            if (!event.getIdentifier().equals("bungeecord:main")) return;

            final ByteArrayDataInput input = ByteStreams.newDataInput(event.getMessage());
            final String subChannel = input.readUTF();

            synchronized (callbackMap) {
                Queue<CompletableFuture<?>> callbacks;

                if (subChannel.equals("IPOther")
                        || subChannel.equals("PlayerCount")
                        || subChannel.equals("PlayerList")
                        || subChannel.equals("UUIDOther")
                        || subChannel.equals("ServerIP")) {
                    final String identifier = input.readUTF(); // server/player name
                    callbacks = callbackMap.get(subChannel + "-" + identifier);
                } else if (subChannel.equals("IP")
                        || subChannel.equals("GetServers")
                        || subChannel.equals("GetServer")
                        || subChannel.equals("UUID")) {
                    callbacks = callbackMap.get(subChannel);
                } else {
                    return;
                }

                if (callbacks == null || callbacks.isEmpty()) return;

                final CompletableFuture<?> callback = callbacks.poll();
                switch (subChannel) {
                    case "IP", "IPOther", "ServerIP" -> {
                        final String ip = input.readUTF();
                        final int port = input.readInt();
                        final InetSocketAddress address = new InetSocketAddress(ip, port);
                        ((CompletableFuture<InetSocketAddress>) callback).complete(address);
                    }
                    case "PlayerCount" -> {
                        final int playerCount = input.readInt();
                        ((CompletableFuture<Integer>) callback).complete(playerCount);
                    }
                    case "PlayerList", "GetServers" -> {
                        final String[] strings = input.readUTF().split(", ");
                        ((CompletableFuture<String[]>) callback).complete(strings);
                    }
                    case "GetServer" -> {
                        final String server = input.readUTF();
                        ((CompletableFuture<String>) callback).complete(server);
                    }
                    case "UUID", "UUIDOther" -> {
                        final UUID uuid = UUID.fromString(input.readUTF());
                        ((CompletableFuture<UUID>) callback).complete(uuid);
                    }
                }
            }
        });
    }

    public void connect(Player player, String server) {
        sendStringMessage(player, "Connect", server);
    }

    public void message(Player player, String playerName, String message) {
        sendStringMessage(player, "Message", playerName, message);
    }

    public void messageRaw(Player player, String playerName, Component component) {
        sendStringMessage(player, "MessageRaw", playerName, GsonComponentSerializer.gson().serialize(component));
    }

    public void kickPlayer(Player player, String playerName, String reason) {
        sendStringMessage(player, "KickPlayer", playerName, reason);
    }

    public void forward(Player player, String server, String subChannel, byte[] message) {
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(server);
        output.writeUTF(subChannel);
        output.writeShort(message.length);
        output.write(message);
        sendMessage(player, "Forward", output.toByteArray());
    }

    public void forwardToPlayer(Player player, String playerName, String channelName, byte[] data) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("ForwardToPlayer");
        output.writeUTF(playerName);
        output.writeUTF(channelName);
        output.writeShort(data.length);
        output.write(data);
        player.sendPluginMessage("BungeeCord", output.toByteArray());
    }

    public CompletableFuture<InetSocketAddress> getIP(Player player) {
        final CompletableFuture<InetSocketAddress> future = new CompletableFuture<>();

        computeCallback("IP", future);
        sendStringMessage(player, "IP");
        return future;
    }

    public CompletableFuture<InetSocketAddress> getIPOther(Player player, String playerName) {
        final CompletableFuture<InetSocketAddress> future = new CompletableFuture<>();

        computeCallback("IPOther-" + playerName, future);
        sendStringMessage(player, "IPOther", playerName);
        return future;
    }

    public CompletableFuture<Integer> getPlayerCount(Player player, String server) {
        final CompletableFuture<Integer> future = new CompletableFuture<>();

        computeCallback("PlayerCount-" + server, future);
        sendStringMessage(player, "PlayerCount", server);
        return future;
    }

    public CompletableFuture<String[]> getPlayerList(Player player, String server) {
        final CompletableFuture<String[]> future = new CompletableFuture<>();

        computeCallback("PlayerList-" + server, future);
        sendStringMessage(player, "PlayerList", server);
        return future;
    }

    public CompletableFuture<String[]> getServers(Player player) {
        final CompletableFuture<String[]> future = new CompletableFuture<>();

        computeCallback("GetServers", future);
        sendStringMessage(player, "GetServers");
        return future;
    }

    public CompletableFuture<String> getServer(Player player) {
        final CompletableFuture<String> future = new CompletableFuture<>();

        computeCallback("GetServer", future);
        sendStringMessage(player, "GetServer");
        return future;
    }

    public CompletableFuture<UUID> getUUID(Player player) {
        final CompletableFuture<UUID> future = new CompletableFuture<>();

        computeCallback("UUID", future);
        sendStringMessage(player, "UUID");
        return future;
    }

    public CompletableFuture<UUID> getUUIDOther(Player player, String playerName) {
        final CompletableFuture<UUID> future = new CompletableFuture<>();

        computeCallback("UUIDOther-" + playerName, future);
        sendStringMessage(player, "UUIDOther", playerName);
        return future;
    }

    public CompletableFuture<InetSocketAddress> getServerIP(Player player, String server) {
        final CompletableFuture<InetSocketAddress> future = new CompletableFuture<>();

        computeCallback("ServerIP-" + server, future);
        sendStringMessage(player, "ServerIP", server);
        return future;
    }

    private void sendMessage(Player player, String subChannel, byte... message) {
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(subChannel);
        output.write(message);

        player.sendPluginMessage("BungeeCord", output.toByteArray());
    }

    private void sendStringMessage(Player player, String subChannel, String... message) {
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(subChannel);
        Arrays.stream(message).forEach(output::writeUTF);

        player.sendPluginMessage("BungeeCord", output.toByteArray());
    }

    private void computeCallback(String key, CompletableFuture<?> future) {
        synchronized (callbackMap) {
            callbackMap.compute(key, (s, futures) -> {
                if (futures == null) futures = new ArrayDeque<>();
                futures.add(future);
                return futures;
            });
        }
    }
}
