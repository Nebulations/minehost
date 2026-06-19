package me.nebu.api.servers;

import me.nebu.api.states.ServerInfo;

public class DockerServerInstance implements ServerInstance {

    private ServerInfo info;

    public DockerServerInstance(ServerInfo info) {
        this.info = info;
    }

    @Override
    public ServerInfo getServerInfo() {
        return info;
    }

    @Override
    public void start() {
        
    }

    @Override
    public void stop() {
        
    }

    @Override
    public void sendCommand(String command) {
        
    }
    
}
