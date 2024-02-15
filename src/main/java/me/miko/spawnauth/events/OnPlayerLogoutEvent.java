package me.miko.spawnauth.events;

import fr.xephi.authme.events.LogoutEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.miko.spawnauth.helpers.SaveHelper;

public class OnPlayerLogoutEvent implements Listener {
    private final SaveHelper saveHelper;

    public OnPlayerLogoutEvent(SaveHelper saveHelper) {
        this.saveHelper = saveHelper;
    }

    @EventHandler
    private void onPlayerLogout(LogoutEvent event) {
        Player player = event.getPlayer();

        saveHelper.saveLocation(player.getName(), player.getLocation());
    }
}
