package com.vendoau.core.pluginmessage;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerPluginMessageEvent;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;

// Inspired by https://github.com/leonardosnt/BungeeChannelApi
public abstract class PluginMessageHandler {

    private final Map<String, Queue<CompletableFuture<?>>> callbackMap = new HashMap<>();

    public PluginMessageHandler(String channel, @Nullable List<String> identifiedSubChannels, @Nullable List<String> unidentifiedSubChannels) {
        MinecraftServer.getGlobalEventHandler().addListener(PlayerPluginMessageEvent.class, event -> {
            if (!event.getIdentifier().equals(channel)) return;

            final ByteArrayDataInput input = ByteStreams.newDataInput(event.getMessage());
            final String subChannel = input.readUTF();

            synchronized (callbackMap) {
                Queue<CompletableFuture<?>> callbacks;

                if (identifiedSubChannels != null && identifiedSubChannels.stream().anyMatch(subChannel::equals)) {
                    final String identifier = input.readUTF();
                    callbacks = callbackMap.get(subChannel + "-" + identifier);
                } else if (unidentifiedSubChannels != null && unidentifiedSubChannels.stream().anyMatch(subChannel::equals)) {
                    callbacks = callbackMap.get(subChannel);
                } else {
                    return;
                }

                if (callbacks == null || callbacks.isEmpty()) return;

                final CompletableFuture<?> callback = callbacks.poll();
                handleCallbacks(input, subChannel, callback);
            }
        });
    }

    public PluginMessageHandler(String channel) {
        this(channel, null, null);
    }

    abstract void handleCallbacks(ByteArrayDataInput input, String subChannel, CompletableFuture<?> callback);

    protected final void sendMessage(Player player, String subChannel, byte... message) {
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(subChannel);
        output.write(message);

        player.sendPluginMessage("BungeeCord", output.toByteArray());
    }

    protected final void sendStringMessage(Player player, String subChannel, String... message) {
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(subChannel);
        Arrays.stream(message).forEach(output::writeUTF);

        player.sendPluginMessage("BungeeCord", output.toByteArray());
    }

    protected final void computeCallback(String key, CompletableFuture<?> future) {
        synchronized (callbackMap) {
            callbackMap.compute(key, (s, futures) -> {
                if (futures == null) futures = new ArrayDeque<>();
                futures.add(future);
                return futures;
            });
        }
    }
}
