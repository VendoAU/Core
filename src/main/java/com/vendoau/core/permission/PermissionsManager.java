package com.vendoau.core.permission;

import com.vendoau.core.CoreExtension;
import com.vendoau.core.util.PermissionsUtil;
import net.luckperms.api.node.Node;
import net.luckperms.api.query.QueryOptions;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.permission.Permission;

import java.util.HashMap;
import java.util.Map;

// FIXME: This whole class sucks
public class PermissionsManager {

    public PermissionsManager() {
        CoreExtension.get().eventNode().addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();

            final Map<String, Boolean> nodeMap = new HashMap<>();
            for (Node node : PermissionsUtil.getPlayerAdapter().getUser(player).resolveDistinctInheritedNodes(QueryOptions.nonContextual())) {
                final String key = node.getKey();
                if (key.startsWith("group.") || key.startsWith("prefix.") || key.startsWith("suffix.")) continue;
                nodeMap.put(node.getKey(), node.getValue());
            }

            nodeMap.forEach((perm, value) -> {
                final Permission permission = new Permission(perm);
                if (value) {
                    player.addPermission(permission);
                } else {
                    player.removePermission(permission);
                }
            });
        });
    }
}
