package me.nebu.api.servers;

import me.nebu.api.states.ServerInfo;

public interface ServerInstance {
    
    ServerInfo getServerInfo();

    void start();
    void stop();

    void sendCommand(String command);

}
