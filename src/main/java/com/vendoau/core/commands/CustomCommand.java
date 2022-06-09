package com.vendoau.core.commands;

import com.vendoau.core.util.PermissionsUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomCommand extends Command {

    protected final String permission;

    public CustomCommand(@NotNull String name, @Nullable String permission) {
        super(name);
        this.permission = permission;

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage(Component.text("Invalid syntax", NamedTextColor.RED));
        });
    }

    public CustomCommand(@NotNull String name) {
        this(name, null);
    }

    protected boolean checkPermission(CommandSender sender) {
        if (hasPermission(sender)) return true;
        sender.sendMessage(Component.text("You don't have permission", NamedTextColor.RED));
        return false;
    }

    public boolean hasPermission(CommandSender sender) {
        if (sender instanceof Player player) {
            return PermissionsUtil.hasPermission(player, permission);
        }
        return true;
    }
}
