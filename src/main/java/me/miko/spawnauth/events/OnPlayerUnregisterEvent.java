package me.miko.spawnauth.events;

import fr.xephi.authme.events.UnregisterByAdminEvent;
import fr.xephi.authme.events.UnregisterByPlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.miko.spawnauth.helpers.SaveHelper;

public class OnPlayerUnregisterEvent implements Listener {
    private final SaveHelper saveHelper;

    public OnPlayerUnregisterEvent(SaveHelper saveHelper) {
        this.saveHelper = saveHelper;
    }

    @EventHandler
    private void onPlayerUnregister(UnregisterByPlayerEvent event) {
        Player player = event.getPlayer();

        saveHelper.saveLocation(player.getName(), player.getLocation());
    }

    @EventHandler
    private void onPlayerUnregisterByAdmin(UnregisterByAdminEvent event) {
        Player player = event.getPlayer();

        if (player != null && player.isOnline()) saveHelper.saveLocation(player.getName(), player.getLocation());
    }
}
