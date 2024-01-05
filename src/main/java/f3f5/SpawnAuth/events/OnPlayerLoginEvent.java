package f3f5.SpawnAuth.events;

import f3f5.SpawnAuth.helpers.GameHelper;
import f3f5.SpawnAuth.helpers.SaveHelper;
import fr.xephi.authme.events.LoginEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerLoginEvent implements Listener {
    private final SaveHelper saveHelper;
    private final GameHelper gameHelper;

    public OnPlayerLoginEvent(GameHelper gameHelper, SaveHelper saveHelper) {
        this.saveHelper = saveHelper;
        this.gameHelper = gameHelper;
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
            gameHelper.teleport(player, location);
            saveHelper.removeLocation(player.getName());
        }
    }
}
