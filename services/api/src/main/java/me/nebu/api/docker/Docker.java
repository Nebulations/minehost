package me.nebu.api.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

public class Docker {

    private static final DockerClient client;

    static {
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("unix:///var/run/docker.sock")
                .build();

        System.out.println("Host: " + config.getDockerHost());
        System.out.println("API Version: " + config.getApiVersion().getVersion());

        client = DockerClientBuilder.getInstance(
                config
        ).build();
    }

    public static DockerClient getClient() {
        return client;
    }
}