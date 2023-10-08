package Revengers.IDE.docker.controller;

import Revengers.IDE.docker.model.CodeResult;
import Revengers.IDE.docker.model.Docker;
import Revengers.IDE.docker.model.RequestImage;
import Revengers.IDE.docker.service.DockerService;
import Revengers.IDE.source.model.Source;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
@RequiredArgsConstructor
@RequestMapping("/docker")
@Slf4j
public class DockerController {

    private DockerClient dockerClient;
    private final DockerService dockerService;

    @PostMapping("/java")
    public ResponseEntity<CodeResult> createJavaContainer(@RequestBody Source source) {
        log.info("fileName={}", source.getFileName());
        log.info("source={}", source.getSource());
        log.info("language={}", source.getLanguageType());
        Docker dockerImage = dockerService.createDockerImage("java");
        String containerId = dockerImage.getContainerId();
        CodeResult codeResult = dockerService.runAsJava(containerId, source);

        return ResponseEntity.ok(codeResult);
    }

    @PostMapping("/python")
    public ResponseEntity<CodeResult> createPythonContainer(@RequestBody Source source) {
        Docker dockerImage = dockerService.createDockerImage("python");
        String containerId = dockerImage.getContainerId();
        CodeResult codeResult = dockerService.runAsPython(containerId, source);

        return ResponseEntity.ok(codeResult);
    }

    /**
     * 도커 연결 테스트용 코드입니다.
     * 이 부분을 호출하고, 오류가 발생한다면 도커와 연결이 이루어지지 않았습니다.
     */
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
