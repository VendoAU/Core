package com.vendoau.core;

import com.vendoau.core.commands.ExtensionsCommand;
import com.vendoau.core.commands.GamemodeCommand;
import com.vendoau.core.commands.HurtCommand;
import com.vendoau.core.commands.KillCommand;
import com.vendoau.core.config.CoreConfig;
import com.vendoau.core.permission.PermissionsManager;
import com.vendoau.core.permission.PrefixManager;
import com.vendoau.core.pluginmessage.BungeeMessageHandler;
import com.vendoau.core.util.RedisUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.entity.Player;
import net.minestom.server.extensions.Extension;

public class CoreExtension extends Extension {

    private static CoreExtension instance;
    private static CoreConfig config;
    private static BungeeMessageHandler bungeeMessageHandler;

    private LuckPerms luckPerms;
    private PrefixManager prefixManager;

    @Override
    public LoadStatus initialize() {
        try {
            luckPerms = LuckPermsProvider.get();
        } catch (IllegalStateException e) {
            return LoadStatus.FAILED;
        }

        instance = this;
        config = new CoreConfig(this);
        bungeeMessageHandler = new BungeeMessageHandler();

        new PermissionsManager();
        prefixManager = new PrefixManager();

        final CommandManager commandManager = MinecraftServer.getCommandManager();
        commandManager.setUnknownCommandCallback((sender, command) -> {
            sender.sendMessage(Component.text("Unknown command", NamedTextColor.RED));
        });
        commandManager.register(new ExtensionsCommand());
        commandManager.register(new GamemodeCommand());
        commandManager.register(new HurtCommand());
        commandManager.register(new KillCommand());

        RedisUtil.publishServerStatus(true);
        RedisUtil.publishPlayerCount();

        return LoadStatus.SUCCESS;
    }

    @Override
    public void terminate() {
        RedisUtil.publishServerStatus(false);
        RedisUtil.publishPlayerCount(0);
    }

    public User getUser(Player player) {
        return luckPerms.getPlayerAdapter(Player.class).getUser(player);
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

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public PrefixManager getPrefixManager() {
        return prefixManager;
    }
}
