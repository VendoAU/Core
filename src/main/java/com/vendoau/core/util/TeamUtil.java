package com.vendoau.core.util;

import net.minestom.server.MinecraftServer;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.scoreboard.Team;
import net.minestom.server.scoreboard.TeamManager;

public final class TeamUtil {

    private TeamUtil() {}

    public static final Team NPC_TEAM;

    static {
        NPC_TEAM = MinecraftServer.getTeamManager().createTeam("NPC-TEAM");
        NPC_TEAM.setNameTagVisibility(TeamsPacket.NameTagVisibility.NEVER);
    }

    public static Team getOrCreateTeam(String name) {
        final TeamManager teamManager = MinecraftServer.getTeamManager();

        Team team = teamManager.getTeam(name);
        if (team == null) {
            team = teamManager.createTeam(name);
        }

        return team;
    }
}
