package me.nebu.api.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

public class Docker {

    private static final DockerClient client =
            DockerClientBuilder.getInstance(
                    DefaultDockerClientConfig.createDefaultConfigBuilder()
                            .withDockerHost("unix:///var/run/docker.sock")
                            .build()
            ).build();

    public static DockerClient getClient() {
        return client;
    }
}