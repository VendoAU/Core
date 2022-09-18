package com.vendoau.core.commands;

import com.vendoau.core.CoreExtension;
import net.minestom.server.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ShowTriggersCommand extends CustomCommand {

    private final List<Player> players = new ArrayList<>();

    public ShowTriggersCommand() {
        super("showtriggers", "core.command.showtriggers");

        addSyntax((sender, context) -> {
            if (sender instanceof Player player) {
                if (players.contains(player)) {
                    players.remove(player);
                    player.sendMessage("Triggers are now hidden");
                    CoreExtension.getTriggerManager().debugTriggers(player, false);
                } else {
                    players.add(player);
                    player.sendMessage("Triggers are now shown");
                    CoreExtension.getTriggerManager().debugTriggers(player, true);
                }
            }
        });
    }
}
