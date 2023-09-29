package Revengers.IDE.docker.controller;

import Revengers.IDE.docker.model.CodeResult;
import Revengers.IDE.docker.model.Docker;
import Revengers.IDE.docker.model.RequestImage;
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
    public ResponseEntity<CodeResult> createJavaContainer(@RequestBody Source source) {
        Docker dockerImage = dockerService.createDockerImage("java");
        String containerId = dockerImage.getContainerId();
        CodeResult codeResult = dockerService.runAsJava(containerId, source);

        return ResponseEntity.ok(codeResult);
    }

    @GetMapping("/python")
    public ResponseEntity<CodeResult> createPythonContainer(@RequestBody Source source) {
        Docker dockerImage = dockerService.createDockerImage("python");
        String containerId = dockerImage.getContainerId();
        CodeResult codeResult = dockerService.runAsPython(containerId, source);

        return ResponseEntity.ok(codeResult);
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

    // 자바 이미지 요청 API
    @GetMapping("/pull/java")
    public ResponseEntity<String> pullJava() throws InterruptedException {
        dockerService.pullDockerJavaImage();
        return ResponseEntity.ok("자바 이미지 요청");
    }

    // 파이썬 이미지 요청 API
    @GetMapping("/pull/python")
    public ResponseEntity<String> pullPython() throws InterruptedException {
        dockerService.pullDockerPythonImage();
        return ResponseEntity.ok("파이썬 이미지 요청");
    }

    @GetMapping("/pull")
    public String pull(@RequestBody RequestImage image) {
        dockerService.pullDockerImage(image);
        return "사용자 요청 이미지를 받아옵니다.";
    }
}
