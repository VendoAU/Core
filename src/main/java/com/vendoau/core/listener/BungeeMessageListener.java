package com.vendoau.core.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.vendoau.core.event.bungee.*;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventDispatcher;
import net.minestom.server.event.player.PlayerPluginMessageEvent;

public class BungeeMessageListener {

    // TODO: Forward and ForwardToPlayer need to be added

    public BungeeMessageListener() {
        MinecraftServer.getGlobalEventHandler().addListener(PlayerPluginMessageEvent.class, event -> {
            if (!event.getIdentifier().equals("bungeecord:main")) return;

            final Player player = event.getPlayer();

            final ByteArrayDataInput input = ByteStreams.newDataInput(event.getMessage());
            final String subChannel = input.readUTF();

            final BungeeResponseEvent bungeeEvent = switch (subChannel) {
                case "IP" -> {
                    final String ip = input.readUTF();
                    final int port = input.readInt();

                    yield new BungeeResponseIPEvent(player, ip, port);
                }
                case "IPOther" -> {
                    final String username = input.readUTF();
                    final String address = input.readUTF();
                    final int port = input.readInt();

                    yield new BungeeResponseIPOtherEvent(player, username, address, port);
                }
                case "PlayerCount" -> {
                    final String server = input.readUTF();
                    final int playerCount = input.readInt();

                    yield new BungeeResponsePlayerCountEvent(player, server, playerCount);
                }
                case "PlayerList" -> {
                    final String server = input.readUTF();
                    final String[] playerList = input.readUTF().split(", ");

                    yield new BungeeResponsePlayerListEvent(player, server, playerList);
                }
                case "GetServers" -> {
                    final String[] servers = input.readUTF().split(", ");

                    yield new BungeeResponseGetServersEvent(player, servers);
                }
                case "GetServer" -> {
                    final String server = input.readUTF();

                    yield new BungeeResponseGetServerEvent(player, server);
                }
                case "UUID" -> {
                    final String uuid = input.readUTF();

                    yield new BungeeResponseUUIDEvent(player, uuid);
                }
                case "UUIDOther" -> {
                    final String playerName = input.readUTF();
                    final String uuid = input.readUTF();

                    yield new BungeeResponseUUIDOtherEvent(player, playerName, uuid);
                }
                case "ServerIP" -> {
                    final String server = input.readUTF();
                    final String ip = input.readUTF();
                    final int port = input.readInt();

                    yield new BungeeResponseServerIPEvent(player, server, ip, port);
                }
                default -> null;
            };

            if (bungeeEvent == null) {
                System.err.println("Received an unknown bungee response");
                return;
            }
            EventDispatcher.call(bungeeEvent);
        });
    }
}
