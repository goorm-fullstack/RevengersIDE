package Revengers.IDE.docker.controller;

import Revengers.IDE.docker.model.CodeResult;
import Revengers.IDE.docker.model.Docker;
import Revengers.IDE.docker.service.DockerService;
import Revengers.IDE.docker.source.model.Source;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CodeResult> createJavaContainer(@RequestBody Source source) {
        Docker dockerImage = dockerService.createDockerImage("java");
        String containerId = dockerImage.getContainerId();
        CodeResult codeResult = dockerService.runAsJava(containerId, source);

        return ResponseEntity.ok(codeResult);
    }

    @GetMapping("/python")
    public String createPythonContainer() {
        Docker dockerImage = dockerService.createDockerImage("python");
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
