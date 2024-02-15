package me.miko.spawnauth.events;

import me.miko.spawnauth.helpers.GameHelper;
import me.miko.spawnauth.helpers.SaveHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoinEvent implements Listener {
    private final GameHelper gameHelper;
    private final SaveHelper saveHelper;

    public OnPlayerJoinEvent(GameHelper gameHelper, SaveHelper saveHelper) {
        this.saveHelper = saveHelper;
        this.gameHelper = gameHelper;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.isDead()) {
            player.spigot().respawn();
            if (player.getBedSpawnLocation() != null) {
                player.teleport(new Location(player.getBedSpawnLocation().getWorld(), player.getBedSpawnLocation().getX(), player.getBedSpawnLocation().getY() + 1.5, player.getBedSpawnLocation().getZ()));
            } else {
                gameHelper.teleport(player, gameHelper.getSpawnLocation(Bukkit.getWorld("world")));
            }
        }
        saveHelper.saveLocation(player.getName(), player.getLocation());
        gameHelper.teleport(player, gameHelper.getSpawnLocation(Bukkit.getWorld("world")));
    }
}