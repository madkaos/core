package com.madkaos.core;

import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.providers.Provider;
import com.dotphin.milkshakeorm.repository.Repository;
import com.madkaos.core.cache.CacheEngine;
import com.madkaos.core.commands.CommandListener;
import com.madkaos.core.commands.admin.AltsCommand;
import com.madkaos.core.commands.admin.BanCommand;
import com.madkaos.core.commands.admin.GameModeCommand;
import com.madkaos.core.commands.admin.MuteCommand;
import com.madkaos.core.commands.admin.TeleportCommand;
import com.madkaos.core.commands.admin.TeleportPosCommand;
import com.madkaos.core.commands.admin.VanishCommand;
import com.madkaos.core.commands.player.FlyCommand;
import com.madkaos.core.commands.player.FriendsCommand;
import com.madkaos.core.commands.player.MessageCommand;
import com.madkaos.core.commands.player.ReplyCommand;
import com.madkaos.core.commands.player.ReportCommand;
import com.madkaos.core.commands.player.menu.MainMenuCommand;
import com.madkaos.core.config.ConfigManager;
import com.madkaos.core.config.Configuration;
import com.madkaos.core.listeners.*;
import com.madkaos.core.messaging.MessageBroker;
import com.madkaos.core.player.MadPlayerManager;
import com.madkaos.core.player.entities.PlayerData;
import com.madkaos.core.player.entities.PlayerPunishment;
import com.madkaos.core.player.entities.PlayerSettings;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MadKaosCore extends JavaPlugin {

    // Managers
    private ConfigManager configManager;
    private MadPlayerManager playerManager;

    private CacheEngine cacheEngine;
    private MessageBroker messageBroker;

    // Repositories
    private Repository<PlayerData> playerDataRepository;
    private Repository<PlayerSettings> playerSettingsRepository;
    private Repository<PlayerPunishment> playerPunishments;

    // Util methods
    public void addCommand(CommandListener listener) {
        listener.register(this, false);
    }

    public void addListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    public void registerChannel(String channel) {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, channel);
    }
    
    @Override
    public void onEnable() {
        // Register channels
        this.registerChannel("BungeeCord");

        // Initialize managers
        this.configManager = new ConfigManager(this);
        this.playerManager = new MadPlayerManager(this);

        // Connect to MongoDB database
        Provider provider = MilkshakeORM.connect(this.getMainConfig().getString("settings.mongodb-uri"));

        this.playerDataRepository = MilkshakeORM.addRepository(PlayerData.class, provider, "Players");
        this.playerSettingsRepository = MilkshakeORM.addRepository(PlayerSettings.class, provider, "PlayerSettings");
        this.playerPunishments = MilkshakeORM.addRepository(PlayerPunishment.class, provider, "PlayerPunishments");

        // Connect to redis cache & pubsub
        String redisURI = this.getMainConfig().getString("settings.redis-uri");
        this.cacheEngine = new CacheEngine(redisURI);
        this.messageBroker = new MessageBroker(this, redisURI).start();

        // Register commands
        this.addCommand(new AltsCommand());
        this.addCommand(new BanCommand());
        this.addCommand(new FlyCommand());
        this.addCommand(new FriendsCommand());
        this.addCommand(new GameModeCommand());
        this.addCommand(new MessageCommand());
        this.addCommand(new MuteCommand());
        this.addCommand(new ReplyCommand());
        this.addCommand(new ReportCommand());
        this.addCommand(new TeleportCommand());
        this.addCommand(new TeleportPosCommand());
        this.addCommand(new VanishCommand());
        this.addCommand(new MainMenuCommand());

        // Register listeners
        this.addListener(new AsyncChatListener(this));
        this.addListener(new CommandPreProcessListener(this));
        this.addListener(new PlayerJoinListener(this));
        this.addListener(new PlayerLoginListener(this));
        this.addListener(new PlayerQuitListener(this));
        this.addListener(new PlayerInteractListener(this));

        // Initialize
        this.playerManager.addAll();
    }

    @Override
    public void onDisable() {
        this.cacheEngine.stop();
        this.messageBroker.stop();
        this.playerManager.clear();
    }

    // Utils
    public boolean isLobby() {
        return this.getMainConfig().getString("server.type").equalsIgnoreCase("lobby");
    }

    // Get managers
    public MadPlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public Configuration getConfig(String name) {
        return this.configManager.getConfig(name);
    }

    public CacheEngine getCacheEngine() {
        return this.cacheEngine;
    }

    public MessageBroker getMessageBroker() {
        return this.messageBroker;
    }

    // Get repositories
    public Repository<PlayerData> getPlayerDataRepository() {
        return this.playerDataRepository;
    }

    public Repository<PlayerSettings> getPlayerSettingsRepository() {
        return this.playerSettingsRepository;
    }

    public Repository<PlayerPunishment> getPlayerPunishmentsRepository() {
        return this.playerPunishments;
    }

    // Get configurations
    public Configuration getMainConfig() {
        return this.configManager.getConfig("config.yml");
    }

    public Configuration getPunishmentConfig() {
        return this.configManager.getConfig("punishments.yml");
    }

    public Configuration getMessages() {
        return this.configManager.getConfig("messages.yml");
    }
}
