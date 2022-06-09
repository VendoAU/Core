package com.vendoau.core.commands;

import com.vendoau.core.CoreExtension;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerSpawnEvent;

public class GamemodeCommand extends CustomCommand {

    public GamemodeCommand() {
        super("gamemode", "core.command.gamemode");

        // Allow players to use gamemode shortcuts (F3+F4, F3+N)
        CoreExtension.get().eventNode().addListener(PlayerSpawnEvent.class, event -> {
            final Player player = event.getPlayer();
            if (hasPermission(player)) {
                player.setPermissionLevel(4);
            }
        });

        final Argument<GameMode> gameModeArgument = ArgumentType.Word("gameMode").from("survival", "creative", "adventure", "spectator").map(name ->
                GameMode.valueOf(name.toUpperCase()));

        addConditionalSyntax((sender, commandString) -> {
            return sender instanceof Player;
        }, (sender, context) -> {
            if (!checkPermission(sender)) return;

            final Player player = (Player) sender;
            player.setGameMode(context.get(gameModeArgument));
        }, gameModeArgument);
    }
}
