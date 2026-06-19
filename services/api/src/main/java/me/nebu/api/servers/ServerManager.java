package me.nebu.api.servers;

import java.util.ArrayList;
import java.util.List;

import me.nebu.api.Database;
import me.nebu.api.states.ServerInfo;
import me.nebu.api.states.ServerState;

public class ServerManager {
    
    private static final List<ServerInstance> serverInstances = new ArrayList<>();

    private static int port = 40000;

    public static ServerInfo start(ServerInfo server) {
        server.setNetworkingInfo("127.0.0.1", port);
        server.setState(ServerState.STARTING);

        // Actually start the server
        ServerInstance instance = new DockerServerInstance(server);
        serverInstances.add(instance);
        instance.start();

        port+=1;
        return server;
    }

    public static void stop(ServerInfo info) {
        String address = info.getAddress();
        int port = info.getPort();

        // TODO: Allow address/port to be reallocated.
        info.setNetworkingInfo(null, -1);
        info.setState(ServerState.OFFLINE);

        Database.update(info);
    }

    public static void sendCommand(ServerInfo info, String command) {
        ServerInstance instance = serverInstances.stream()
            .filter(i -> i.getServerInfo().getId().equals(info.getId()))
            .findFirst().orElse(null);

        if (instance == null) return;

        instance.sendCommand(command);
    }

}
