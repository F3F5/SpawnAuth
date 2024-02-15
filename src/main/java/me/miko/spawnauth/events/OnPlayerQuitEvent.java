package me.miko.spawnauth.events;

import me.miko.spawnauth.helpers.GameHelper;
import me.miko.spawnauth.helpers.SaveHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuitEvent implements Listener {
    private final GameHelper gameHelper;
    private final SaveHelper saveHelper;

    public OnPlayerQuitEvent(GameHelper gameHelper, SaveHelper saveHelper) {
        this.saveHelper = saveHelper;
        this.gameHelper = gameHelper;
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (player.isInsideVehicle()) player.leaveVehicle();
        if (!gameHelper.authMeApi.isAuthenticated(player))
            gameHelper.teleport(player, saveHelper.getLocation(player.getName()));
    }
}
