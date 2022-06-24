package com.vendoau.core.scoreboard;

import net.kyori.adventure.text.Component;
import net.minestom.server.scoreboard.Sidebar;
import org.jetbrains.annotations.NotNull;

public class EmptyScoreboardLine extends Sidebar.ScoreboardLine {

    public EmptyScoreboardLine(@NotNull String id, int line) {
        super(id, Component.empty(), line);
    }
}
