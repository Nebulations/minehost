package me.nebu.api.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

public class Docker {

    private static final DockerClient client;

    static {
        DockerClientConfig config =
                new DefaultDockerClientConfig.Builder()
                        .withDockerHost("unix:///var/run/docker.sock")
                        .build();

        DockerHttpClient httpClient =
                new ApacheDockerHttpClient.Builder()
                        .dockerHost(config.getDockerHost())
                        .sslConfig(config.getSSLConfig())
                        .build();

        System.out.println("DOCKER HOST=" + config.getDockerHost());

        client = DockerClientImpl.getInstance(config, httpClient);
    }

    public static DockerClient getClient() {
        return client;
    }
}