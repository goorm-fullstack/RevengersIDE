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
    // 현재는 테스트용 코드입니다.
    // 도커 컨테이너 생성 확인
    private String createJavaContainer() {
        return dockerClient.createContainerCmd("alpine")
                .withCmd("ps")
                .withName("test")
                .withHostName("whitetkim")
                .exec()
                .getId();
    }

    
    // 도커 연결 확인
    public void test() {
        dockerClient.pingCmd().exec();
    }

    // 이미지 리스트 출력
    public List<Image> imageList() {
        return dockerClient.listImagesCmd().exec();
    }

    // 컨테이너 리스트 출력
    public List<Container> containerList() {
        return dockerClient.listContainersCmd().exec();
    }

    // 테스트 성공
    public void pullDockerImage() throws InterruptedException {
        dockerClient.pullImageCmd("alpine")//사용하려는 이미지 이름인듯
                .withTag("latest")//버전같은 정보
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS );
    }
}
