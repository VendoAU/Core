package com.vendoau.core.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

public class KillCommand extends Command {

    public KillCommand() {
        super("kill");

        addSyntax((sender, context) -> {
            if (sender instanceof Player player) {
                player.kill();
            }
        });
    }
}
