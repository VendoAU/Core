package com.vendoau.core.util;

import net.kyori.adventure.text.Component;
import net.minestom.server.adventure.audience.Audiences;

public final class TextUtil {

    private TextUtil() {}

    public static void messageAllPlayers(Component message) {
        Audiences.players().sendMessage(message);
    }

    public static void messageAllPlayers(String message) {
        messageAllPlayers(Component.text(message));
    }
}
