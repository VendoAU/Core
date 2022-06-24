package com.vendoau.core.util;

import net.minestom.server.MinecraftServer;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.scoreboard.Team;

public class TeamUtil {

    public static final Team NPC_TEAM;

    static {
        NPC_TEAM = MinecraftServer.getTeamManager().createTeam("NPC-TEAM");
        NPC_TEAM.setNameTagVisibility(TeamsPacket.NameTagVisibility.NEVER);
    }
}
