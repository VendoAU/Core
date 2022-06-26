package com.vendoau.core.server;

import java.net.InetSocketAddress;

public class Server {

    private final String name;
    private final InetSocketAddress address;
    private int playerCount;
    private boolean online;

    public Server(String name, InetSocketAddress address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
