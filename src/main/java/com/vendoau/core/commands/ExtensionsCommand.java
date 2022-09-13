package com.vendoau.core.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;
import net.minestom.server.extensions.ExtensionDescriptor;
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

            final List<Component> extensionComponents = new ArrayList<>();
            for (Extension extension : extensions) {
                final ExtensionDescriptor descriptor = extension.descriptor();
                final String name = descriptor.name();
                final String version = descriptor.version();
                final List<String> authors = descriptor.authors();

                Component hoverComponent = Component.text(name)
                        .append(Component.newline())
                        .append(Component.text("Version: " + version));
                if (authors.size() == 1) {
                    hoverComponent = hoverComponent
                            .append(Component.newline())
                            .append(Component.text("Author: " + authors.get(0)));
                } else if (authors.size() != 0) {
                    hoverComponent = hoverComponent
                            .append(Component.newline())
                            .append(Component.text("Authors: " + String.join(", ", authors)));
                }

                final Component extensionComponent = Component.text(name)
                        .hoverEvent(hoverComponent);

                extensionComponents.add(extensionComponent);
            }

            sender.sendMessage(Component.join(JoinConfiguration.commas(true), extensionComponents));
        });
    }
}
