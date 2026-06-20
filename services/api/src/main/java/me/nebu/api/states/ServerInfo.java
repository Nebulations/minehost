package me.nebu.api.states;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "servers")
public class ServerInfo {
    
    @DatabaseField(id = true)
    private String id;

    @DatabaseField(columnName = "owner")
    private String owner;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "state")
    private String state;

    @DatabaseField(columnName = "address")
    private String address;

    @DatabaseField(columnName = "port", defaultValue = "-1")
    private int port;

    @DatabaseField(columnName = "container-id")
    private String containerId;

    @DatabaseField(columnName = "container-name")
    private String containerName;

    private ServerInfo() {}

    public ServerInfo(String id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.state = ServerState.OFFLINE.name();
        this.containerId = null;
        this.containerName = null;
        this.address = null;
        this.port = -1;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
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

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setNetworkingInfo(String address, int port) {
        this.address = address;
        this.port = port;
    }
}
