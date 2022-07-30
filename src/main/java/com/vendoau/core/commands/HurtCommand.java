package com.vendoau.core.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.DamageType;

public class HurtCommand extends Command {

    public HurtCommand() {
        super("hurt");

        final ArgumentInteger amount = new ArgumentInteger("hurtAmount");

        addSyntax((sender, context) -> {
            if (sender instanceof Player player) {
                player.damage(DamageType.VOID, context.get(amount));
            }
        }, amount);
    }
}
