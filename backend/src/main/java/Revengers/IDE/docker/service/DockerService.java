package Revengers.IDE.docker.service;

import Revengers.IDE.docker.dto.request.RequestCompileDto;
import Revengers.IDE.docker.model.Docker;
import Revengers.IDE.docker.repository.DockerRepository;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class DockerService {
    private final DockerClient dockerClient;
    private final HostConfig hostConfig;
    private final DockerRepository dockerRepository;

    // name: imageName, options: 자바 or 파이썬
    public Docker createDockerImage(String name, String options) throws InterruptedException, IOException {
        String containerId = createContainer(options);
        Docker docker = Docker.builder()
                .containerId(containerId)
                .imageName(name)
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
                .withHostName("whiteKim")
                .exec()
                .getId();
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
    public void pullDockerJavaImage() throws InterruptedException {
        dockerClient.pullImageCmd("openjdk")//사용하려는 이미지 이름인듯
                .withTag("22-ea-16-jdk")//버전같은 정보
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS );
    }

    // 컨테이너 실행
    public void startContainer() throws InterruptedException, IOException {
        String containerId = createDockerImage("openjdk", "java").getContainerId();
        dockerClient.startContainerCmd(containerId).exec();
    }

    // 컴파일
    // 컴파일을 실행할 때 컨테이너를 생성해야 로그 결과를 가져올 수 있을 거 같다.
    // 로컬의 java파일을 컨테이너로 복사하여 컴파일 후 실행한다.
    public String compile() throws IOException, InterruptedException {
        // 컨테이너 생성
        String id = dockerClient.createContainerCmd("openjdk:22-ea-16-jdk")
                .withName("test5")
                .withHostName("whitetkim")
                .withCmd("sh", "-c", "javac /home/Test.java && java -cp /home Test") // 컨테이너가 실행될 때 실행할 명령어(startContainerCmd 메서드가 실행되면 작동한다.)
                .exec()
                .getId();

        // 로컬에 저장한 자바 파일을 컨테이너의 home 폴더 안으로 복사한다.
        // ../../ -> 이렇게 설정해야 C: 경로부터 시작이 됨
        //todo: 현재는 로컬로 테스트했지만 서버 내에 파일을 저장하여 실행하는 것 도전 중 or 다른 방법??
        dockerClient.copyArchiveToContainerCmd(id)
                .withHostResource("../../baekjoon/src/Test.java") // 제 로컬의 테스트용 자바 파일 경로
                .withRemotePath("/home")
                .exec();

        dockerClient.startContainerCmd(id).exec(); // 컨테이너 실행

        return getContainerLogs(id); // 실행 후 컨테이너 로그 출력 -> 밑에 메소드 있음
    }

    // 이 메소드는 정확히 어떻게 돌아가는지 잘 모르겠다..... -> gpt의 힘..
    private String getContainerLogs(String containerId) throws InterruptedException {
        LogContainerCmd logContainerCmd = dockerClient.logContainerCmd(containerId)
                .withStdOut(true)
                .withStdErr(true)
                .withFollowStream(true)
                .withTailAll();

        CountDownLatch latch = new CountDownLatch(1);
        ByteArrayOutputStream logOutput = new ByteArrayOutputStream();

        // 로그를 캡처하여 ByteArrayOutputStream에 저장
        logContainerCmd.exec(new LogContainerResultCallback() {
            @Override
            public void onNext(Frame item) {
                try {
                    logOutput.write(item.getPayload());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to capture container logs.", e);
                }
            }

            @Override
            public void onComplete() {
                latch.countDown(); // 작업 완료 시 CountDownLatch 감소
            }
        });

        latch.await(); // 로그 캡처 완료 대기

        // 로그를 문자열로 반환
        return logOutput.toString();
    }
}
