package com.vendoau.core.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;
import net.minestom.server.extensions.ExtensionManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExtensionsCommand extends CustomCommand {

    public ExtensionsCommand() {
        super("extensions", "core.command.extensions");

        addSyntax((sender, context) -> {
            if (!checkPermission(sender)) return;

            final ExtensionManager extensionManager = MinecraftServer.getExtensionManager();
            final Collection<Extension> extensions = extensionManager.getExtensions();
            sender.sendMessage("Extensions (" + extensions.size() + "):");

            final List<String> extensionNames = new ArrayList<>();
            extensions.forEach(extension -> {
                extensionNames.add(extension.descriptor().name() + "(" + extension.descriptor().version() + ")");
            });

            sender.sendMessage(String.join(", ", extensionNames));
        });
    }
}
