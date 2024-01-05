package f3f5.SpawnAuth.events;

import f3f5.SpawnAuth.helpers.SaveHelper;
import fr.xephi.authme.events.LoginEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerLoginEvent implements Listener {
    private final SaveHelper saveHelper;

    public OnPlayerLoginEvent(SaveHelper saveHelper) {
        this.saveHelper = saveHelper;
    }

    @EventHandler
    private void onPlayerLogin(LoginEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        Location location = saveHelper.getLocation(name);

        player.setNoDamageTicks(20);
        player.setFallDistance(0);
        player.setGravity(true);
        if (location != null) {
            player.teleport(location);
            saveHelper.removeLocation(player.getName());
        }
    }
}
