package com.vendoau.core.permission;

import com.vendoau.core.CoreExtension;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.model.user.User;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.scoreboard.Team;
import net.minestom.server.scoreboard.TeamManager;

public class PrefixManager {

    private boolean show = true;

    public PrefixManager() {
        // Name tag and scoreboard
        CoreExtension.get().eventNode().addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            final User user = CoreExtension.get().getUser(player);

            final Team team = getOrCreateTeam(user.getCachedData().getMetaData().getPrimaryGroup());
            team.setPrefix(MiniMessage.miniMessage().deserialize(user.getCachedData().getMetaData().getPrefix()));

            if (!show) return;

            showPrefix(event.getPlayer());
        });

        // Chat
        CoreExtension.get().eventNode().addListener(PlayerChatEvent.class, event -> {
            final Player player = event.getPlayer();
            final User user = CoreExtension.get().getUser(player);

            final Component prefix = MiniMessage.miniMessage().deserialize(user.getCachedData().getMetaData().getPrefix());
            final Component name = prefix.append(Component.text(player.getUsername(), NamedTextColor.WHITE));
            final Component format = Component.text()
                    .append(name)
                    .append(Component.text(": ", NamedTextColor.GRAY))
                    .append(Component.text(event.getMessage()))
                    .build();
            event.setChatFormat(playerChatEvent -> format);
        });
    }

    // TODO: Move to Library
    public static Team getOrCreateTeam(String name) {
        final TeamManager teamManager = MinecraftServer.getTeamManager();

        Team team = teamManager.getTeam(name);
        if (team == null) {
            team = teamManager.createTeam(name);
        }

        return team;
    }

    private void showPrefix(Player player) {
        final User user = CoreExtension.get().getUser(player);

        final Team team = getOrCreateTeam(user.getPrimaryGroup());
        player.setTeam(team);
    }

    public void showPrefixes() {
        show = true;
        MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(this::showPrefix);
    }

    public void hidePrefix(Player player) {
        final Team team = player.getTeam();
        if (team != null) {
            team.removeMember(player.getUsername());
        }
    }

    public void hidePrefixes() {
        show = false;
        MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(this::hidePrefix);
    }
}
