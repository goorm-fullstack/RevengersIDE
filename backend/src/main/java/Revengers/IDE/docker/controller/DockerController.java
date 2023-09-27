package Revengers.IDE.docker.controller;

import Revengers.IDE.docker.model.Docker;
import Revengers.IDE.docker.service.DockerService;
import Revengers.IDE.docker.source.model.Source;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/docker")
public class DockerController {

    private final DockerService dockerService;

    @GetMapping("/java")
    public ResponseEntity<DockerService.CodeResult> createJavaContainer(@RequestBody Source source) throws InterruptedException {
        Docker dockerImage = dockerService.createDockerImage("java");
        String containerId = dockerImage.getContainerId();
        DockerService.CodeResult codeResult = dockerService.runAsJava(containerId, source);

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
        dockerService.pullDockerImage();
        return "이미지 요청";
    }
}
