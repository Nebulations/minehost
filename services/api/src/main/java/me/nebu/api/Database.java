package me.nebu.api;

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
        try {
            JdbcConnectionSource src = new JdbcConnectionSource("jdbc:mariadb://"
                    + System.getenv("DB_HOST")
                    + ":" + System.getenv("DB_PORT")
                    + "/" + System.getenv("DB_NAME")
            );

            src.setUsername(System.getenv("DB_USERNAME"));
            src.setPassword(System.getenv("DB_PASSWORD"));

            TableUtils.createTableIfNotExists(src, ServerInfo.class);
            servers = DaoManager.createDao(src, ServerInfo.class);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
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
        } catch (SQLException ignored) {}
    }

    public static void update(ServerInfo serverInfo) {
        try {
            servers.update(serverInfo);
        } catch (SQLException ignored) {}
    }

    public static List<ServerInfo> getServers() {
        try {
            return servers.queryForAll();
        } catch (SQLException e) {
            return null;
        }
    }

}
