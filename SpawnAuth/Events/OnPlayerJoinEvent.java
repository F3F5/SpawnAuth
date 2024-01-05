package F3f5.SpawnAuth.Events;

import F3f5.SpawnAuth.Helpers.GameHelper;
import F3f5.SpawnAuth.Helpers.SaveHelper;
import F3f5.SpawnAuth.SpawnAuth;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static org.bukkit.Bukkit.getScheduler;

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
                player.teleport(gameHelper.getSpawnLocation(Bukkit.getWorld("world")));
            }
        }
        saveHelper.saveLocation(player.getName(), player.getLocation());
        getScheduler().scheduleSyncDelayedTask(SpawnAuth.getPlugin(SpawnAuth.class), () -> {
            player.setGravity(false);
            player.teleport(new Location(player.getWorld(), 0, 10000, 0));
        }, 2);
    }
}
