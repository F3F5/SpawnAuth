package f3f5.SpawnAuth;

import F3f25.SpawnAuth.events.*;
import F3f5.SpawnAuth.events.*;
import f3f5.SpawnAuth.events.*;
import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.plugin.java.JavaPlugin;
import f3f5.SpawnAuth.helpers.GameHelper;
import f3f5.SpawnAuth.helpers.SaveHelper;

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

        // Register events
        getServer().getPluginManager().registerEvents(new OnPlayerJoinEvent(gameHelper, saveHelper), this);
        getServer().getPluginManager().registerEvents(new OnPlayerLoginEvent(saveHelper), this);
        getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(gameHelper, saveHelper), this);
        getServer().getPluginManager().registerEvents(new OnPlayerLogoutEvent(saveHelper), this);
        getServer().getPluginManager().registerEvents(new OnPlayerUnregisterEvent(saveHelper), this);
    }
}
