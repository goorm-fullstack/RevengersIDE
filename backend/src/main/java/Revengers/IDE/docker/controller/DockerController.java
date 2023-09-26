package Revengers.IDE.docker.controller;

import Revengers.IDE.docker.model.Docker;
import Revengers.IDE.docker.service.DockerService;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/docker")
public class DockerController {

    private final DockerService dockerService;

    @GetMapping("/java")
    public String createJavaContainer() {
        Docker dockerImage = dockerService.createDockerImage("java", "java");
        return dockerImage.getContainerName();
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
