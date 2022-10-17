package com.vendoau.core.commands;

import com.vendoau.core.CoreExtension;
import com.vendoau.core.trigger.BetterBox;
import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;

public class SelectionCommand extends Command {

    public SelectionCommand() {
        super("selection");

        addSubcommand(new ClearSubCommand());
        addSubcommand(new GetSubCommand());
    }

    private static class ClearSubCommand extends Command {

        public ClearSubCommand() {
            super("clear");

            addSyntax((sender, context) -> {
                if (sender instanceof Player player) {
                    CoreExtension.getSelectionTool().clear(player);
                    player.sendMessage("Cleared selection");
                }
            });
        }
    }

    private static class GetSubCommand extends Command {

        public GetSubCommand() {
            super("get");

            addSyntax((sender, context) -> {
                if (sender instanceof Player player) {
                    final Point[] positions = CoreExtension.getSelectionTool().get(player);
                    if (positions == null) {
                        player.sendMessage("You don't have a selection");
                        return;
                    }

                    final Point pos1 = positions[0];
                    final Point pos2 = positions[1];

                    final BetterBox box = new BetterBox(pos1, pos2);
                    final Point start = box.boundingBox().relativeStart();
                    final Point end = box.boundingBox().relativeEnd();
                    player.sendMessage("Pos1, Pos2:");
                    player.sendMessage(pos1.x() + ", " + pos1.y() + ", " + pos1.z() + ", " + pos2.x() + ", " + pos2.y() + ", " + pos2.z());
                    player.sendMessage("Start, End:");
                    player.sendMessage(start.x() + ", " + start.y() + ", " + start.z() + ", " + end.x() + ", " + end.y() + ", " + end.z());
                }
            });
        }
    }
}
