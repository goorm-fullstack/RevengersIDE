package Revengers.IDE.docker.controller;

import Revengers.IDE.docker.dto.request.RequestCompileDto;
import Revengers.IDE.docker.model.Docker;
import Revengers.IDE.docker.service.DockerService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
@RequiredArgsConstructor
@RequestMapping("/docker")
public class DockerController {

    private DockerClient dockerClient;
    private final DockerService dockerService;

    @GetMapping("/java")
    public String createJavaContainer() throws InterruptedException, IOException {
        Docker dockerImage = dockerService.createDockerImage("openjdk", "java");
        return dockerImage.getContainerId();
    }

    @GetMapping("/test")
    public String test() {
        dockerService.test();
        return "test";
    }
    
    @GetMapping("/list")
    public List<Image> imageList() {
        return dockerService.imageList();
    }

    @GetMapping("/container")
    public List<Container> containerList() {
        return dockerService.containerList();
    }
    
    @GetMapping("/pull")
    public String pull() throws InterruptedException {
        dockerService.pullDockerJavaImage();
        return "이미지 요청";
    }

    @GetMapping("/start")
    public String start() throws InterruptedException, IOException {
        dockerService.startContainer();
        return "실행 성공";
    }

    @PostMapping("/compile")
    public String compile() throws IOException, InterruptedException {
       return dockerService.compile();
    }
}
