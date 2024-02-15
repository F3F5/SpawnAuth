package me.miko.spawnauth.helpers;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Random;

public class GameHelper {
    public AuthMeApi authMeApi;
    public SaveHelper saveHelper;

    public GameHelper(AuthMeApi authMeApi) {
        this.authMeApi = authMeApi;
    }

    public Location getSpawnLocation(World world) {
        int spawnRadius = Integer.parseInt(world.getGameRuleValue("spawnRadius")) <= 10 ? 200 : Integer.parseInt(world.getGameRuleValue("spawnRadius"));

        int x = new Random().nextInt(spawnRadius * 2) - spawnRadius;
        int z = new Random().nextInt(spawnRadius * 2) - spawnRadius;
        double y = world.getHighestBlockYAt(x, z);

        return new Location(world, x, y, z);
    }

    public void teleport(Player player, Location location) {
        player.teleport(location);

        if (location.getWorld() == Bukkit.getWorld("world_nether") && player.getLocation().getY() >= 127) {
            double y = player.getLocation().getY() - saveHelper.getLocation(player.getName()).getY();
            player.teleport(new Location(location.getWorld(), location.getX(), player.getLocation().getY() - y, location.getZ()));
        }
    }

    public void setSaveHelper(SaveHelper saveHelper) {
        this.saveHelper = saveHelper;
    }
}
