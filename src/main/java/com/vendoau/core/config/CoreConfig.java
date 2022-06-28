package com.vendoau.core.config;

import com.vendoau.core.CoreExtension;
import com.vendoau.core.util.ConfigUtil;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

public class CoreConfig {

    private String serverName;

    public CoreConfig(CoreExtension core) {
        try {
            final HoconConfigurationLoader loader = ConfigUtil.getLoader(core);
            final CommentedConfigurationNode config = loader.load();
            ConfigUtil.merge(loader, config, core, "/core.conf");

            serverName = config.node("serverName").getString();
        } catch (ConfigurateException e) {
            core.logger().error("An error occurred while trying to read the config");
            e.printStackTrace();
        }
    }

    public String getServerName() {
        return serverName;
    }
}
