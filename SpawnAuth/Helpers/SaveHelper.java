package F3f5.SpawnAuth.Helpers;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.sql.SQLException;
import java.sql.*;

public class SaveHelper {
    private final String dataBaseURL;

    public SaveHelper(File dataBaseFolder) {
        this.dataBaseURL = "jdbc:sqlite:" + dataBaseFolder + File.separator + "SpawnAuth.db";
    }

    public void setupDataBase() {
        try (Connection connection = DriverManager.getConnection(dataBaseURL)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS PlayerLocations (name TEXT NOT NULL, x INTEGER NOT NULL, y INTEGER NOT NULL, z INTEGER NOT NULL, world TEXT NOT NULL)")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ignored) {
        }
    }

    public void saveLocation(String name, Location location) {
        removeLocation(name);
        try (Connection connection = DriverManager.getConnection(dataBaseURL)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PlayerLocations (name,x,y,z,world) VALUES (?,?,?,?,?)")) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(5, location.getWorld().getName());

                preparedStatement.setInt(2, (int) location.getX());
                preparedStatement.setInt(3, (int) location.getY());
                preparedStatement.setInt(4, (int) location.getZ());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ignored) {
        }
    }

    public void removeLocation(String name) {
        try (Connection connection = DriverManager.getConnection(dataBaseURL)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PlayerLocations WHERE name = ?")) {
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ignored) {
        }
    }

    public Location getLocation(String name) {
        try (Connection connection = DriverManager.getConnection(dataBaseURL)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PlayerLocations WHERE name = ?")) {
                preparedStatement.setString(1, name);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    return new Location(Bukkit.getWorld(result.getString("world")), result.getInt("x"), result.getInt("y"), result.getInt("z"));
                }
            }
        } catch (SQLException ignored) {
        }
        return null;
    }

}
