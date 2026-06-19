package me.nebu.persistence;

public class ServerInfo {

    private final String id;
    private final String name;
    private final String state;
    private final String address;
    private final int port;

    public ServerInfo(String id, String name, String state, String address, int port) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.address = address;
        this.port = port;
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

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}

