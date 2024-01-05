package f3f5.SpawnAuth.helpers;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Location;
import org.bukkit.World;
import java.util.Random;

public class GameHelper {
    public AuthMeApi authMeApi;

    public GameHelper(AuthMeApi authMeApi) {
        this.authMeApi = authMeApi;
    }

    public Location getSpawnLocation(World world) {
        int spawnRadius = Integer.parseInt(world.getGameRuleValue("spawnRadius")) <= 10 ? 200 : Integer.parseInt(world.getGameRuleValue("spawnRadius"));

        int x = new Random().nextInt(spawnRadius*2)-spawnRadius;
        int z = new Random().nextInt(spawnRadius*2)-spawnRadius;
        double y = world.getHighestBlockYAt(x, z);

        return new Location(world, x, y, z);
    }
}
