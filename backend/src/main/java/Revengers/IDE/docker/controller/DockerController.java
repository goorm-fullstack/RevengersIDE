package Revengers.IDE.docker.controller;

import Revengers.IDE.docker.exception.WrongLangTypeException;
import Revengers.IDE.docker.model.CodeResult;
import Revengers.IDE.docker.model.Docker;
import Revengers.IDE.docker.model.RequestImage;
import Revengers.IDE.docker.service.DockerService;
import Revengers.IDE.member.exception.LoginException;
import Revengers.IDE.member.model.Member;
import Revengers.IDE.member.service.MemberService;
import Revengers.IDE.source.model.Source;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/docker")
public class DockerController {

    private final DockerService dockerService;
    private final MemberService memberService;

    //최소 페이지 진입시 사용할 함수
    //셀렉트 박스로 값을 선택할 때마다 이 부분을 요청합시다.
    @GetMapping
    public ResponseEntity<CodeResult> initPage(Authentication auth, @RequestParam String lang) {
        if(auth != null) {
            Member loginMember = memberService.getLoginByMemberId(auth.getName());
            if(loginMember != null) {
                CodeResult previousCode = dockerService.getPreviousCode(loginMember, lang);
                return ResponseEntity.ok(previousCode);
            }
        }
        throw new LoginException("로그인이 필요한 서비스입니다.");
    }

    // 실제 코드를 돌리는 함수입니다.
    @PostMapping("/run")
    public ResponseEntity<CodeResult> runContainer(Authentication auth, @RequestBody Source source) {
        if (auth != null) {
            Member loginMember = memberService.getLoginByMemberId(auth.getName());
            if (loginMember != null) {
                List<Docker> docker = loginMember.getDocker();
                if(docker.isEmpty()) {//없다면 만든다. 있다면 재활용
                    CodeResult result = dockerService.createDockerImageAndRun(source, loginMember);
                    return ResponseEntity.ok(result);
                } else {//재활용
                    boolean check = false;
                    Docker index = null;
                    for(Docker container : docker) {
                        if(Objects.equals(container.getLangType(), source.getLanguageType())) {
                            check = true;
                            index = container;
                            break;
                        }
                    }

                    if(check) {//찾은 컨테이너에서 코드를 실행합니다.
                        CodeResult result = dockerService.runCode(source, index.getContainerId());
                        return ResponseEntity.ok(result);
                    } else {// 해당 타입 컨테이너가 없으므로 새로 생성
                        CodeResult result = dockerService.createDockerImageAndRun(source, loginMember);
                        return ResponseEntity.ok(result);
                    }
                }
            }
        }
        throw new WrongLangTypeException("잘못된 입력입니다.");
    }

    @PostMapping("/java")
    public ResponseEntity<CodeResult> createJavaContainer(@RequestBody Source source) {
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
