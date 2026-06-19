package me.nebu.api.states;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "servers")
public class ServerInfo {
    
    @DatabaseField(id = true)
    private String id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "state")
    private String state;

    @DatabaseField(columnName = "address")
    private String address;

    @DatabaseField(columnName = "port", defaultValue = "-1")
    private int port;

    @SuppressWarnings("unused")
    private ServerInfo() {}

    public ServerInfo(String id, String name) {
        this.id = id;
        this.name = name;
        this.state = ServerState.OFFLINE.name();
        this.address = null;
        this.port = -1;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ServerState getState() {
        return ServerState.valueOf(state);
    }

    public void setState(ServerState state) {
        this.state = state.name();
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public void setNetworkingInfo(String address, int port) {
        this.address = address;
        this.port = port;
    }
}
