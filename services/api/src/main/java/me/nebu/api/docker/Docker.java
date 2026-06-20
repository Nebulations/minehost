package me.nebu.api.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import java.time.Duration;

public class Docker {

    private static final DockerClientConfig config =
            DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .build();

    private static final DockerHttpClient httpClient =
            new ApacheDockerHttpClient.Builder()
                    .dockerHost(config.getDockerHost())
                    .sslConfig(config.getSSLConfig())
                    .build();

    private static final DockerClient client =
            DockerClientImpl.getInstance(config, httpClient);

    public static DockerClient getClient() {
        return client;
    }

}
