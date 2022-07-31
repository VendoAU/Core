package com.vendoau.core.util;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.platform.PlayerAdapter;
import net.minestom.server.entity.Player;

public final class PermissionsUtil {

    private PermissionsUtil() {}

    public static PlayerAdapter<Player> getPlayerAdapter() {
        return LuckPermsProvider.get().getPlayerAdapter(Player.class);
    }

    public static User getUser(Player player) {
        return getPlayerAdapter().getUser(player);
    }

    public static boolean hasPermission(Player player, String permission) {
        return getPlayerAdapter().getPermissionData(player).checkPermission(permission).asBoolean();
    }
}
