package Revengers.IDE.docker.service;

import Revengers.IDE.docker.model.CodeResult;
import Revengers.IDE.docker.model.Docker;
import Revengers.IDE.docker.repository.DockerRepository;
import Revengers.IDE.docker.service.callback.TimeoutResultCallback;
import Revengers.IDE.docker.source.model.Source;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.async.ResultCallbackTemplate;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DockerService {
    private final DockerClient dockerClient;
    private final HostConfig hostConfig;
    private final DockerRepository dockerRepository;


    @Value("${username")
    private String username;

    public Docker createDockerImage(String options) {
        String containerId = createContainer(options);
        Docker docker = Docker.builder()
                .containerId(containerId)
                .build();

        return dockerRepository.save(docker);
    }


    // 사용자가 선택한 언어로 컨테이너를 생성
    private String createContainer(String options) throws InterruptedException, IOException {
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
        return dockerClient.createContainerCmd("openjdk:22-ea-16-jdk")
                .withName("java_"+ UUID.randomUUID().toString())
                .withHostName(username)
                .exec()
                .getId();
    }

    // 실제로 컨테이너에서 소스 코드를 실행하는 로직
    public CodeResult runAsJava(String containerId, Source source) {
        dockerClient.startContainerCmd(containerId).exec();
        return compileJava(containerId, source);
    }

    /**
     * 입력받은 자바 소스 코드를 컨테이너에 저장하고, 이를 컴파일한 후에 실행하는 로직
     */
    private CodeResult compileJava(String containerId, Source source) {
        CodeResult codeResult = new CodeResult();
        String[] saveSourceCommand = {"sh", "-c", "echo '" + source.getSource() + "' > /usr/src/" + source.getFileName()};// 파일 이동 명령
        String[] compileCommand = {"sh", "-c", "javac /usr/src/" + source.getFileName()};// 컴파일 명령
        String[] runCommand = {"sh", "-c","java /usr/src/" + source.getFileName()};// 실행 명령
        StringBuilder standardOutputLogs = new StringBuilder();// 결과 출력
        StringBuilder standardErrorLogs = new StringBuilder();// 에러 출력
        StringBuilder exceptions = new StringBuilder();// 예외 출력
        ResultCallbackTemplate resultCallbackTemplate = new TimeoutResultCallback(standardOutputLogs, standardErrorLogs, exceptions);//필요없을거 같네
        try {
            // Java 소스 코드를 컨테이너 내부에 저장

            ExecCreateCmdResponse saveSourceResponse = dockerClient.execCreateCmd(containerId)
                    .withCmd(saveSourceCommand)
                    .exec();
            boolean sourceSaved = dockerClient.execStartCmd(saveSourceResponse.getId()).exec(resultCallbackTemplate).awaitCompletion(5, TimeUnit.SECONDS);

            // Java 코드 컴파일
            ExecCreateCmdResponse compileResponse = dockerClient.execCreateCmd(containerId)
                    .withAttachStdout(true)
                    .withAttachStderr(true)
                    .withCmd(compileCommand)
                    .exec();
            boolean compileSuccess = dockerClient.execStartCmd(compileResponse.getId()).exec(resultCallbackTemplate).awaitCompletion(10, TimeUnit.SECONDS);

            // Java 코드 실행
            ExecCreateCmdResponse runResponse = dockerClient.execCreateCmd(containerId)
                    .withAttachStdout(true)
                    .withAttachStderr(true)
                    .withCmd(runCommand)
                    .exec();
            boolean runSuccess = dockerClient.execStartCmd(runResponse.getId())
                    .exec(new ResultCallback.Adapter<>() {  // 일단은 Deprecated된 인터페이스나 클래스를 사용하는 것은 좋지 않을 것 같습니다.
                        /**
                         * Object의 데이터 형식
                         * StreamType() : 위에서 사용설정한 STDOUT, STDERR
                         * 결과값은 바이트 단위로 출력된다. 바이트 값 -> 문자 값으로 변환 로직 추가
                         */
                        @Override
                        public void onNext(Frame object) {
                            //System.out.println(object);  //for example
                            if(object.getStreamType().name().equals("STDOUT")) {
                                byte[] data = object.getPayload();// 실행 출력값
                                String output = new String(data, UTF_8);//변환
                                standardOutputLogs.append(output);
                            } else if(object.getStreamType().name().equals("STDERR")) {
                                byte[] data = object.getPayload();// 에러 출력값
                                String output = new String(data, UTF_8);//변환
                                standardErrorLogs.append(output);
                            }
                        }
                    })
                    .awaitCompletion(60, TimeUnit.SECONDS);

            if (!sourceSaved || !compileSuccess || !runSuccess) {
                codeResult.setExceptions("Code execution failed.");
            }
            codeResult.setStandardOutput(standardOutputLogs.toString());
            codeResult.setStandardError(standardErrorLogs.toString());
            codeResult.setExceptions(exceptions.toString());
        } catch (InterruptedException e) {
            codeResult.setExceptions("Execution interrupted: " + e.getMessage());
            throw new RuntimeException("일단 미리 예외를 만들어두자");//나중에 적절한 예외를 만들어서 사용합시다.
        }
        return codeResult;
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
        return dockerClient.listContainersCmd()
                .withShowSize(true)
                .withShowAll(true)
                .exec();
    }

    // 테스트 성공
    public void pullDockerImage() throws InterruptedException {
        dockerClient.pullImageCmd("openjdk")//사용하려는 이미지 이름인듯
                .withTag("22-ea-16-jdk")//버전같은 정보
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS );
    }
}
