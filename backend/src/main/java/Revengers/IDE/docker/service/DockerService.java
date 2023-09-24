package Revengers.IDE.docker.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.HostConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DockerService {
    private final DockerClient dockerClient;
    private final HostConfig hostConfig;

    public String createContainer() {
        CreateContainerResponse container = dockerClient.createContainerCmd("nginx")
                .withHostConfig(hostConfig)
                .exec();

        return container.getId();
    }
}
