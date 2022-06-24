package com.vendoau.core.pluginmessage;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.minestom.server.entity.Player;

import java.util.Arrays;

public class BungeeMessage {

    public static void connect(Player player, String server) {
        sendStringMessage(player, "Connect", server);
    }

    public static void connectOther(Player player, String otherName, String server) {
        sendStringMessage(player, "ConnectOther", otherName, server);
    }

    public static void ip(Player player) {
        sendStringMessage(player, "IP");
    }

    public static void ipOther(Player player, String otherName) {
        sendStringMessage(player, "IPOther", otherName);
    }

    public static void playerCount(Player player, String server) {
        sendStringMessage(player, "PlayerCount", server);
    }

    public static void playerList(Player player, String server) {
        sendStringMessage(player, "PlayerList", server);
    }

    public static void getServers(Player player) {
        sendStringMessage(player, "GetServers");
    }

    public static void message(Player player, String message) {
        sendStringMessage(player, "Message", message);
    }

    public static void messageRaw(Player player, String otherName, String message) {
        sendStringMessage(player, "MessageRaw", otherName, message);
    }

    public static void getServer(Player player) {
        sendStringMessage(player, "GetServer");
    }

    public static void forward(Player player, String server, String subChannel, byte[] message) {
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(server);
        output.writeUTF(subChannel);
        output.writeShort(message.length);
        output.write(message);

        sendMessage(player, "Forward", output.toByteArray());
    }

    public static void forwardToPlayer(Player player, String otherName, String subChannel, byte[] message) {
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(otherName);
        output.writeUTF(subChannel);
        output.writeShort(message.length);
        output.write(message);

        sendMessage(player, "ForwardToPlayer", output.toByteArray());
    }

    public static void uuid(Player player) {
        sendStringMessage(player, "UUID");
    }

    public static void uuidOther(Player player, String otherName) {
        sendStringMessage(player, "UUIDOther", otherName);
    }

    public static void serverIp(Player player, String server) {
        sendStringMessage(player, "ServerIP", server);
    }

    public static void kickPlayer(Player player, String otherPlayer, String reason) {
        sendStringMessage(player, "KickPlayer", otherPlayer, reason);
    }

    private static void sendMessage(Player player, String subChannel, byte... message) {
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(subChannel);
        output.write(message);

        player.sendPluginMessage("BungeeCord", output.toByteArray());
    }

    private static void sendStringMessage(Player player, String subChannel, String... message) {
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(subChannel);
        Arrays.stream(message).forEach(output::writeUTF);

        player.sendPluginMessage("BungeeCord", output.toByteArray());
    }
}
