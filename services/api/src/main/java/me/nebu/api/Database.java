package me.nebu.api;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import me.nebu.api.states.ServerInfo;

public class Database {

    private static Dao<ServerInfo, String> servers;

    public static void start() {
        File databaseFile = new File("C:/");

        try {
            JdbcConnectionSource src = new JdbcConnectionSource("jdbc:sqlite:" + databaseFile.getAbsolutePath());

            TableUtils.createTableIfNotExists(src, ServerInfo.class);
            servers = DaoManager.createDao(src, ServerInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ServerInfo getServerFromId(String id) {
        try {
            return servers.queryForId(id);
        } catch (SQLException e) {
            return null;
        }
    }

    public static ServerInfo getServerFromName(String name) {
        try {
            return servers.queryForEq("name", name).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public static void create(ServerInfo serverInfo) {
        try {
            servers.create(serverInfo);
        } catch (SQLException e) {}
    }

    public static void update(ServerInfo serverInfo) {
        try {
            servers.update(serverInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<ServerInfo> getServers() {
        try {
            return servers.queryForAll();
        } catch (SQLException e) {
            return null;
        }
    }

}
