package Revengers.IDE.docker.controller;

import Revengers.IDE.docker.exception.WrongLangTypeException;
import Revengers.IDE.docker.model.CodeResult;
import Revengers.IDE.docker.model.Docker;
import Revengers.IDE.docker.model.RequestImage;
import Revengers.IDE.docker.service.DockerService;
import Revengers.IDE.docker.service.SourceCommand;
import Revengers.IDE.member.exception.LoginException;
import Revengers.IDE.member.model.Member;
import Revengers.IDE.member.service.MemberService;
import Revengers.IDE.source.model.SourceRequestDto;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/docker")
@Slf4j
public class DockerController {

    private DockerClient dockerClient;
    private final DockerService dockerService;
    private final MemberService memberService;

    //최소 페이지 진입시 사용할 함수
    //셀렉트 박스로 값을 선택할 때마다 이 부분을 요청합시다.
    @GetMapping
    public ResponseEntity<CodeResult> initPage(Authentication auth, @RequestParam String lang) {
        if (auth == null) {
            throw new LoginException("로그인이 필요한 서비스입니다.");
        }

        Member loginMember = memberService.getLoginByMemberId(auth.getName());

        if (loginMember == null) {
            throw new LoginException("로그인이 필요한 서비스입니다.");
        }

        CodeResult previousCode = dockerService.getPreviousCode(loginMember, lang);
        return ResponseEntity.ok(previousCode);
        // 조건문이나 괄호가 중첩되는것은 가독성이 좋지않다.
    }

    // 실제 코드를 돌리는 함수입니다.
    @PostMapping("/run")
    public ResponseEntity<CodeResult> runContainer(Authentication auth,
        @RequestBody SourceRequestDto sourceRequestDto) {
        if (auth != null) { // TODO 시간남으면
            Member loginMember = memberService.getLoginByMemberId(auth.getName());
            if (loginMember != null) {
                List<Docker> docker = loginMember.getDocker();
                if (docker.isEmpty()) {//없다면 만든다. 있다면 재활용
                    var command = SourceCommand.builder().sourceId(sourceRequestDto.getId()).source(sourceRequestDto.getSource()).build();
                    CodeResult result = dockerService.createDockerImageAndRun(sourceRequestDto, loginMember);
                    return ResponseEntity.ok(result);
                } else {//재활용
                    boolean check = false;
                    Docker index = null;
                    for (Docker container : docker) {
                        if (Objects.equals(container.getLangType(), sourceRequestDto.getLanguageType())) {
                            check = true;
                            index = container;
                            break;
                        }
                    }

                    if (check) {//찾은 컨테이너에서 코드를 실행합니다.
                        CodeResult result = dockerService.runCode(sourceRequestDto, index.getContainerId());
                        return ResponseEntity.ok(result);
                    } else {// 해당 타입 컨테이너가 없으므로 새로 생성
                        CodeResult result = dockerService.createDockerImageAndRun(sourceRequestDto,
                            loginMember);
                        return ResponseEntity.ok(result);
                    }
                }
            }
        }

        // controller(view) -> service(domain logic) <- repository(data persistence)
        // 유저이름을 중간에 마스킹 해달라
        // 의존성: 변하는것이 변하지않는것에 의존한다.
        // 의존성이 없을수는없고, 반드시 가져야하지만 양방향 의존성은 거의 좋지않다.
        // Mapstruct
        // @mapping(soruce="lType", target="languageType")
        // interface convert(SourceRequestDto: dto): SourceCommand
        // var command = mapper.convert(dto)



        return "someObject";



        // 코드 작성에 들이는 시간 2 1
        // 코드 읽는데 들이는 시간 8 9
        // 괄호가 중첩 ( if-else => early return )
//        early-return
//        if( auth != null ) {
//            return null;
//        }else {
//            return "someObject";
//        }
//
//        if( auth != null ) {
//            return null;
//        }
        // if-
        throw new WrongLangTypeException("잘못된 입력입니다.");
    }

    @PostMapping("/java")
    public ResponseEntity<CodeResult> createJavaContainer(@RequestBody SourceRequestDto sourceRequestDto) {
        log.info("source={}", sourceRequestDto.getSource());
        log.info("language={}", sourceRequestDto.getLanguageType());
        Docker dockerImage = dockerService.createDockerImage("java");
        String containerId = dockerImage.getContainerId();
        CodeResult codeResult = dockerService.runAsJava(containerId, sourceRequestDto);

        return ResponseEntity.ok(codeResult);
    }

    @PostMapping("/python")
    public ResponseEntity<CodeResult> createPythonContainer(@RequestBody SourceRequestDto sourceRequestDto) {
        Docker dockerImage = dockerService.createDockerImage("python");
        String containerId = dockerImage.getContainerId();
        CodeResult codeResult = dockerService.runAsPython(containerId, sourceRequestDto);

        return ResponseEntity.ok(codeResult);
    }

    /**
     * 도커 연결 테스트용 코드입니다. 이 부분을 호출하고, 오류가 발생한다면 도커와 연결이 이루어지지 않았습니다.
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
