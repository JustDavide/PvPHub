package me.dovide.pvphub;

import me.dovide.pvphub.commands.SwordGive;
import me.dovide.pvphub.listener.*;
import me.dovide.pvphub.utils.Config;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public final class HubMain extends JavaPlugin {

    Logger logger = Logger.getLogger("HubPvp");
    Config config;

    @Override
    public void onEnable() {

        logger.info("Plugin starting up. Made by Dovide");

        config = createConfig("config.yml");

        getCommand("givehubsword").setExecutor(new SwordGive(this));

        getServer().getPluginManager().registerEvents(new SwitchListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryTouch(this), this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(), this);
        getServer().getPluginManager().registerEvents(new AttackListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);

    }

    // Creates (or loads) a yml file with that name
    public Config createConfig(String name) {
        File fc = new File(getDataFolder(), name);
        if (!fc.exists()) {
            fc.getParentFile().mkdir();
            saveResource(name, false);
        }
        Config config = new Config();
        try {
            config.load(fc);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        this.config = config;
        return config;
    }

    @Override
    public Config getConfig(){
        return config;
    }
}
