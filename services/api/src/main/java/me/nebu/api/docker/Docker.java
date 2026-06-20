package me.nebu.api.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;

public class Docker {

    private static final DockerClient client = DockerClientBuilder.getInstance().build();

    public static DockerClient getClient() {
        return client;
    }

}
