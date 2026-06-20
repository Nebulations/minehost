package me.nebu.api.servers;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import me.nebu.api.Database;
import me.nebu.api.docker.Docker;
import me.nebu.api.states.ServerInfo;
import me.nebu.api.states.ServerState;

public class DockerServerInstance implements ServerInstance {

    private final ServerInfo info;
    private final int port;

    public DockerServerInstance(ServerInfo info, int port) {
        this.info = info;
        this.port = port;
    }

    @Override
    public ServerInfo getServerInfo() {
        return info;
    }

    @Override
    public void start() {
        System.out.println("-----");
        System.out.println("Creating docker host");
        System.out.println("ID=" + info.getId());
        System.out.println("NAME=" + info.getName());;
        System.out.println("-----");

        CreateContainerResponse container = Docker.getClient()
                .createContainerCmd("minehost-paper")
                .withName("minehost-srv-" + info.getId())
                .withHostConfig(
                        HostConfig.newHostConfig()
                                .withNetworkMode("minehost")
                                .withBinds(
                                        new Bind(
                                                "/srv/minehost/servers/" + info.getId(),
                                                new Volume("/data")
                                        )
                                ).withPortBindings(
                                        new PortBinding(
                                                Ports.Binding.bindPort(port),
                                                new ExposedPort(25565)
                                        )
                                )
                ).exec();

        info.setContainerId(container.getId());
        info.setNetworkingInfo(
                "minehost-srv-" + info.getId(),
                port
        );
        info.setState(ServerState.STARTING);

        Database.update(info);

        Docker.getClient()
                .startContainerCmd(container.getId()).exec();
    }

    @Override
    public void stop() {
        Docker.getClient()
                .stopContainerCmd(info.getContainerId())
                .exec();

        Docker.getClient()
                .removeContainerCmd(info.getContainerId())
                .exec();

        info.setState(ServerState.OFFLINE);

        Database.update(info);
    }

    @Override
    public void sendCommand(String command) {
        
    }
    
}
