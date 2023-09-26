package Revengers.IDE.docker.service;

import Revengers.IDE.docker.model.Docker;
import Revengers.IDE.docker.repository.DockerRepository;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DockerService {
    private final DockerClient dockerClient;
    private final HostConfig hostConfig;
    private final DockerRepository dockerRepository;

    public Docker createDockerImage(String name, String options) {
        String containerId = createContainer(options);
        Docker docker = Docker.builder()
                .containerName(containerId)
                .imageName(name)
                .build();

        return dockerRepository.save(docker);
    }


    // 사용자가 선택한 언어로 컨테이너를 생성
    private String createContainer(String options) {
        String containerId = "";//컨테이너 아이디
        switch (options) {
            case "java" :
                containerId = createJavaContainer();
                return containerId;
            case "python" :
                containerId = createPythonContainer();
                return containerId;
            default:
                throw new IllegalArgumentException("잘못된 선택값입니다.");
        }
    }

    // 파이썬 컨테이너 생성
    private String createPythonContainer() {
        CreateContainerResponse container = dockerClient.createContainerCmd("python")
                .withHostConfig(hostConfig)
                .exec();

        return container.getId();
    }

    // 자바 컨테이너 생성
    private String createJavaContainer() {
        String image = dockerClient.buildImageCmd()
                .withDockerfile(new File("./Dockerfile"))
                .withTag("alpine:git")
                .exec(new BuildImageResultCallback())
                .awaitImageId();

        return image;
    }

    public void test() {
        dockerClient.pingCmd().exec();
    }

    public List<Container> imageList() {
        return dockerClient.listContainersCmd().exec();
    }

    public void pullDockerImage() throws InterruptedException {
        dockerClient.pullImageCmd("repository")
                .withTag("alpine:git")
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS ) ;
    }
}
