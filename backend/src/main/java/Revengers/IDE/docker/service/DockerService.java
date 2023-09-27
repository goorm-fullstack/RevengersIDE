package Revengers.IDE.docker.service;

import Revengers.IDE.docker.model.Docker;
import Revengers.IDE.docker.repository.DockerRepository;
import Revengers.IDE.docker.service.callback.TimeoutResultCallback;
import Revengers.IDE.docker.source.model.Source;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallbackTemplate;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
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
        return dockerClient.createContainerCmd("openjdk:22-ea-16-jdk")
                .withName("java_"+ UUID.randomUUID().toString())
                .withHostName(username)
                .exec()
                .getId();
    }

    // 실제로 컨테이너에서 소스 코드를 실행하는 로직
    public CodeResult runAsJava(String containerId, Source source) throws InterruptedException {
        dockerClient.startContainerCmd(containerId).exec();
        return compileJava(containerId, source);
    }

    private CodeResult compileJava(String containerId, Source source) {
        CodeResult codeResult = new CodeResult();
        String[] saveSourceCommand = {"sh", "-c", "echo '" + source.getSource() + "' > /usr/src/" + source.getFileName()};
        String[] compileCommand = {"sh", "-c", "javac /usr/src/" + source.getFileName()};
        String[] runCommand = {"sh", "-c","java /usr/src/" + source.getFileName()};
        StringBuilder standardOutputLogs = new StringBuilder();
        StringBuilder standardErrorLogs = new StringBuilder();
        StringBuilder exceptions = new StringBuilder();
        ResultCallbackTemplate resultCallbackTemplate = new TimeoutResultCallback(standardOutputLogs, standardErrorLogs, exceptions);
        try {
            // Java 소스 코드를 컨테이너 내부에 저장

            ExecCreateCmdResponse saveSourceResponse = dockerClient.execCreateCmd(containerId)
                    .withAttachStdout(true)
                    .withAttachStderr(true)
                    .withAttachStdin(true)
                    .withCmd(saveSourceCommand)
                    .exec();
            boolean sourceSaved = dockerClient.execStartCmd(saveSourceResponse.getId()).exec(resultCallbackTemplate).awaitCompletion(5, TimeUnit.SECONDS);

            // Java 코드 컴파일
            ExecCreateCmdResponse compileResponse = dockerClient.execCreateCmd(containerId)
                    .withAttachStdout(true)
                    .withAttachStderr(true)
                    .withAttachStdin(true)
                    .withCmd(compileCommand)
                    .exec();
            boolean compileSuccess = dockerClient.execStartCmd(compileResponse.getId()).exec(resultCallbackTemplate).awaitCompletion(10, TimeUnit.SECONDS);

            // Java 코드 실행
            ExecCreateCmdResponse runResponse = dockerClient.execCreateCmd(containerId)
                    .withAttachStdout(true)
                    .withAttachStderr(true)
                    .withAttachStdin(true)
                    .withCmd(runCommand)
                    .exec();
            boolean runSuccess = dockerClient.execStartCmd(runResponse.getId()).exec(resultCallbackTemplate).awaitCompletion(10, TimeUnit.SECONDS);

            if (!sourceSaved || !compileSuccess || !runSuccess) {
                codeResult.setExceptions("Code execution failed.");
            }

            codeResult.setStandardOutput(standardOutputLogs.toString());
            codeResult.setStandardError(standardErrorLogs.toString());
            codeResult.setExceptions(exceptions.toString());
        } catch (InterruptedException e) {
            codeResult.setExceptions("Execution interrupted: " + e.getMessage());
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

    static public class CodeResult {
        private String standardOutput;
        private String standardError;
        private String exceptions;

        public String getStandardOutput() {
            return standardOutput;
        }

        public void setStandardOutput(String standardOutput) {
            this.standardOutput = standardOutput;
        }

        public String getStandardError() {
            return standardError;
        }

        public void setStandardError(String standardError) {
            this.standardError = standardError;
        }

        public String getExceptions() {
            return exceptions;
        }

        public void setExceptions(String exceptions) {
            this.exceptions = exceptions;
        }
    }
}
