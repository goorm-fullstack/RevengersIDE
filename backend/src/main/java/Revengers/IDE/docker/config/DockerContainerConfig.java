package Revengers.IDE.docker.config;

import Revengers.IDE.docker.model.Docker;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerContainerConfig {

    @Bean
    public DockerClient dockerClient() {
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        return DockerClientBuilder.getInstance(config).build();
    }

    @Bean
    public HostConfig hostConfig() {
        HostConfig hostConfig = HostConfig.newHostConfig()
                .withPortBindings(PortBinding.parse("8080:80"));
        return hostConfig;
    }
}
