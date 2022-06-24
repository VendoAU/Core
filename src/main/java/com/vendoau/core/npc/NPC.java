package com.vendoau.core.npc;

import com.vendoau.core.util.PacketUtil;
import com.vendoau.core.util.TeamUtil;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.Nullable;

public class NPC extends Entity {

    private final String username;
    private final PlayerSkin skin;

    public NPC(String username, @Nullable PlayerSkin skin, Instance instance, Pos pos) {
        super(EntityType.PLAYER);

        this.username = username;
        this.skin = skin;

        setInstance(instance, pos);
        TeamUtil.NPC_TEAM.addMember(username);

        MinecraftServer.getGlobalEventHandler().addListener(PlayerEntityInteractEvent.class, event -> {
            final Player player = event.getPlayer();
            final Entity target = event.getTarget();

            if (target.getUuid().equals(uuid)) {
                onInteract(player);
            }
        });
    }

    public NPC(String username, Instance instance, Pos pos) {
        this(username, null, instance, pos);
    }

    protected void onInteract(Player player) {}

    public void sendToPlayer(Player player) {
        player.sendPacket(PacketUtil.addPlayerInfoPacket(uuid, username, skin));
        addViewer(player);
    }

    public void removeFromTab(Player player) {
        player.sendPacket(PacketUtil.removePlayerInfoPacket(uuid));
    }

    public String getUsername() {
        return username;
    }

    public PlayerSkin getSkin() {
        return skin;
    }
}