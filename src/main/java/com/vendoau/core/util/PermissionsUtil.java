package com.vendoau.core.util;

import com.vendoau.core.CoreExtension;
import net.luckperms.api.platform.PlayerAdapter;
import net.minestom.server.entity.Player;

public final class PermissionsUtil {

    private PermissionsUtil() {}

    public static PlayerAdapter<Player> getPlayerAdapter() {
        return CoreExtension.get().getLuckPerms().getPlayerAdapter(Player.class);
    }

    public static boolean hasPermission(Player player, String permission) {
        return getPlayerAdapter().getPermissionData(player).checkPermission(permission).asBoolean();
    }
}
