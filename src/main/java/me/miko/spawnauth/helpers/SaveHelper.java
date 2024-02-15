package me.miko.spawnauth.helpers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
            try (PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS PlayerLocations (name TEXT NOT NULL, x TEXT NOT NULL, y TEXT NOT NULL, z TEXT NOT NULL, world TEXT NOT NULL)")) {
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

                preparedStatement.setString(2, String.valueOf(location.getX()));
                preparedStatement.setString(3, String.valueOf(location.getY()));
                preparedStatement.setString(4, String.valueOf(location.getZ()));
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
                    return new Location(Bukkit.getWorld(result.getString("world")), Double.parseDouble(result.getString("x")), Double.parseDouble(result.getString("y")), Double.parseDouble(result.getString("z")));
                }
            }
        } catch (SQLException ignored) {
        }
        return null;
    }

    public void handleDisable(GameHelper gameHelper) {
        try (Connection connection = DriverManager.getConnection(dataBaseURL)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PlayerLocations")) {
                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    try {
                        Location location = getLocation(result.getString("name"));
                        Player player = Bukkit.getPlayer(result.getString("name"));

                        gameHelper.teleport(player, location);
                        removeLocation(player.getName());
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (SQLException ignored) {
        }
    }
}