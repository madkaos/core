package com.madkaos.core;

import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.providers.Provider;
import com.dotphin.milkshakeorm.repository.Repository;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.commands.admin.GameModeCommand;
import com.madkaos.core.commands.admin.TeleportCommand;
import com.madkaos.core.commands.admin.TeleportPosCommand;
import com.madkaos.core.commands.admin.VanishCommand;
import com.madkaos.core.commands.player.FlyCommand;
import com.madkaos.core.commands.player.FriendsCommand;
import com.madkaos.core.config.ConfigManager;
import com.madkaos.core.config.Configuration;
import com.madkaos.core.listeners.PlayerJoinListener;
import com.madkaos.core.listeners.PlayerQuitListener;
import com.madkaos.core.player.MadPlayerManager;
import com.madkaos.core.player.entities.PlayerData;
import com.madkaos.core.player.entities.PlayerSettings;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MadKaosCore extends JavaPlugin {

    // Managers
    private ConfigManager configManager;
    private MadPlayerManager playerManager;

    // Repositories
    private Repository<PlayerData> playerDataRepository;
    private Repository<PlayerSettings> playerSettingsRepository;

    // Util methods
    public void addCommand(CommandListener listener) {
        listener.register(this, false);
    }

    public void addListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
    
    @Override
    public void onEnable() {
        // Initialize managers
        this.configManager = new ConfigManager(this);
        this.playerManager = new MadPlayerManager(this);

        // Connect to MongoDB database
        Provider provider = MilkshakeORM.connect(this.getMainConfig().getString("settings.mongodb-uri"));

        this.playerDataRepository = MilkshakeORM.addRepository(PlayerData.class, provider, "Players");
        this.playerSettingsRepository = MilkshakeORM.addRepository(PlayerSettings.class, provider, "PlayerSettings");

        // Register commands
        this.addCommand(new FlyCommand());
        this.addCommand(new FriendsCommand());
        this.addCommand(new GameModeCommand());
        this.addCommand(new TeleportCommand());
        this.addCommand(new TeleportPosCommand());
        this.addCommand(new VanishCommand());

        // Register listeners
        this.addListener(new PlayerJoinListener(this));
        this.addListener(new PlayerQuitListener(this));
    }

    // Get managers
    public MadPlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public Configuration getConfig(String name) {
        return this.configManager.getConfig(name);
    }

    // Get repositories
    public Repository<PlayerData> getPlayerDataRepository() {
        return this.playerDataRepository;
    }

    public Repository<PlayerSettings> getPlayerSettingsRepository() {
        return this.playerSettingsRepository;
    }

    // Get configurations
    public Configuration getMainConfig() {
        return this.configManager.getConfig("config.yml");
    }

    public Configuration getMessages() {
        return this.configManager.getConfig("messages.yml");
    }
}
