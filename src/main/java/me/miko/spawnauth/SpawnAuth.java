package me.miko.spawnauth;

import me.miko.spawnauth.events.*;
import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.plugin.java.JavaPlugin;
import me.miko.spawnauth.helpers.GameHelper;
import me.miko.spawnauth.helpers.SaveHelper;

import java.util.logging.Logger;

public final class SpawnAuth extends JavaPlugin {
    public SaveHelper saveHelper;
    public GameHelper gameHelper;
    public Logger logger;

    @Override
    public void onEnable() {
        // Setup logger
        logger = getLogger();

        // Setup dataFolder
        if (!getDataFolder().mkdirs() && !getDataFolder().exists()) {
            logger.severe("DataBase folder failed to create.");
            getServer().shutdown();
        }

        // Create classes
        saveHelper = new SaveHelper(getDataFolder());
        gameHelper = new GameHelper(AuthMeApi.getInstance());

        // Setup data base
        saveHelper.setupDataBase();
        gameHelper.setSaveHelper(saveHelper);

        // Register events
        getServer().getPluginManager().registerEvents(new OnPlayerJoinEvent(gameHelper, saveHelper), this);
        getServer().getPluginManager().registerEvents(new OnPlayerLoginEvent(gameHelper, saveHelper), this);
        getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(gameHelper, saveHelper), this);
        getServer().getPluginManager().registerEvents(new OnPlayerLogoutEvent(saveHelper), this);
        getServer().getPluginManager().registerEvents(new OnPlayerUnregisterEvent(saveHelper), this);
    }

    @Override
    public void onDisable() {
        saveHelper.handleDisable(gameHelper);
    }
}
