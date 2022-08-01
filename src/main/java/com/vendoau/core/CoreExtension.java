package com.vendoau.core;

import com.vendoau.core.commands.ExtensionsCommand;
import com.vendoau.core.commands.GamemodeCommand;
import com.vendoau.core.commands.HurtCommand;
import com.vendoau.core.commands.KillCommand;
import com.vendoau.core.config.CoreConfig;
import com.vendoau.core.listener.EntityPreDeathListener;
import com.vendoau.core.permission.PermissionsManager;
import com.vendoau.core.permission.PrefixManager;
import com.vendoau.core.pluginmessage.BungeeMessageHandler;
import com.vendoau.core.util.RedisUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extensions.Extension;

public class CoreExtension extends Extension {

    private static CoreExtension instance;
    private static CoreConfig config;
    private static BungeeMessageHandler bungeeMessageHandler;
    private static PrefixManager prefixManager;

    @Override
    public LoadStatus initialize() {
        instance = this;
        config = new CoreConfig(this);
        bungeeMessageHandler = new BungeeMessageHandler();

        new PermissionsManager();
        prefixManager = new PrefixManager();

        // Listeners
        new EntityPreDeathListener(this);

        // Commands
        final CommandManager commandManager = MinecraftServer.getCommandManager();
        commandManager.setUnknownCommandCallback((sender, command) -> {
            sender.sendMessage(Component.text("Unknown command", NamedTextColor.RED));
        });
        commandManager.register(new ExtensionsCommand());
        commandManager.register(new GamemodeCommand());
        commandManager.register(new HurtCommand());
        commandManager.register(new KillCommand());

        // Publish redis stuff
        RedisUtil.publishServerStatus(true);
        RedisUtil.publishPlayerCount();
        eventNode().addListener(PlayerLoginEvent.class, event -> {
            RedisUtil.publishPlayerCount();
        }).addListener(PlayerDisconnectEvent.class, event -> {
            RedisUtil.publishPlayerCount();
        });

        return LoadStatus.SUCCESS;
    }

    @Override
    public void terminate() {
        RedisUtil.publishServerStatus(false);
        RedisUtil.publishPlayerCount(0);
    }

    public static CoreExtension get() {
        return instance;
    }

    public static CoreConfig getConfig() {
        return config;
    }

    public static BungeeMessageHandler getBungeeMessageHandler() {
        return bungeeMessageHandler;
    }

    public static PrefixManager getPrefixManager() {
        return prefixManager;
    }
}
