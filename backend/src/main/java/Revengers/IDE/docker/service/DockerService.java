package Revengers.IDE.docker.service;

import Revengers.IDE.docker.exception.CustomInterruptedException;
import Revengers.IDE.docker.exception.WrongLangTypeException;
import Revengers.IDE.docker.model.CodeResult;
import Revengers.IDE.docker.model.Docker;
import Revengers.IDE.docker.model.RequestImage;
import Revengers.IDE.docker.repository.DockerRepository;
import Revengers.IDE.docker.service.callback.TimeoutResultCallback;
import Revengers.IDE.member.model.Member;
import Revengers.IDE.source.model.Source;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.async.ResultCallbackTemplate;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DockerService {
    private final DockerClient dockerClient;
    private final DockerRepository dockerRepository;


    @Value("${username}")
    private String username;

    public Docker createDockerImage(String options) {
        String containerId = createContainer(options);
        Docker docker = Docker.builder()
                .containerId(containerId)
                .langType(options)
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
        return dockerClient.createContainerCmd("python:3.10.13-bookworm")
                .withName("python_"+UUID.randomUUID().toString())
                .withHostName(username)
                .withTty(true)
                .exec()
                .getId();
    }

    // 실제로 컨테이너에서 소스 코드를 실행하는 로직
    public CodeResult runAsPython(String containerId, Source source) {
        dockerClient.startContainerCmd(containerId).exec();
        return compilePython(containerId, source);
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
        String[] saveSourceCommand = {"sh", "-c", "echo '" + source.getSource() + "' > /usr/src/Main.java"};// 파일 이동 명령
        String[] compileCommand = {"sh", "-c", "javac /usr/src/Main.java"};// 컴파일 명령
        String[] runCommand = {"sh", "-c","java /usr/src/Main.java"};// 실행 명령
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
            throw new CustomInterruptedException("인터럽트 예외 발생");
        }
        return codeResult;
    }

    /**
     * 입력받은 자바 소스 코드를 컨테이너에 저장하고, 이를 컴파일한 후에 실행하는 로직
     */
    private CodeResult compilePython(String containerId, Source source) {
        CodeResult codeResult = new CodeResult();
        String[] saveSourceCommand = {"sh", "-c", "echo '" + source.getSource() + "' > /usr/src/Main.py"};// 파일 이동 명령
        String[] runCommand = {"sh", "-c", "python3 /usr/src/Main.py"};// 컴파일 명령
        StringBuilder standardOutputLogs = new StringBuilder();// 결과 출력
        StringBuilder standardErrorLogs = new StringBuilder();// 에러 출력
        StringBuilder exceptions = new StringBuilder();// 예외 출력
        ResultCallbackTemplate resultCallbackTemplate = new TimeoutResultCallback(standardOutputLogs, standardErrorLogs, exceptions);//필요없을거 같네
        try {
            // Python 코드를 저장
            ExecCreateCmdResponse saveSourceResponse = dockerClient.execCreateCmd(containerId)
                    .withCmd(saveSourceCommand)
                    .withAttachStdout(true)
                    .withAttachStderr(true)
                    .exec();
            boolean sourceSaved = dockerClient.execStartCmd(saveSourceResponse.getId()).exec(resultCallbackTemplate).awaitCompletion(5, TimeUnit.SECONDS);

            // Python 코드 실행 -> 파이썬은 스크립트 언어라서 따로 컴파일 과정이 없습니다. 참고
            ExecCreateCmdResponse runResponse = dockerClient.execCreateCmd(containerId)
                    .withAttachStdout(true)
                    .withAttachStderr(true)
                    .withAttachStdin(true) // stdin 활성화
                    .withTty(true) // TTY 활성화
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


            if (!sourceSaved || !runSuccess) {
                codeResult.setExceptions("Code execution failed.");
            }
            codeResult.setStandardOutput(standardOutputLogs.toString());
            codeResult.setStandardError(standardErrorLogs.toString());
            codeResult.setExceptions(exceptions.toString());
        } catch (InterruptedException e) {
            codeResult.setExceptions("Execution interrupted: " + e.getMessage());
            throw new CustomInterruptedException("인터럽트 예외 발생");
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

    // 사용자의 이전 작업 코드를 출력하는 코드입니다.
    public CodeResult getPreviousCode(Member member) {
        CodeResult codeResult = new CodeResult();
        String langType = member.getDocker().getLangType();
        String fileName = "Main";
        switch (langType) {
            case "java" :
                fileName += ".java";
                break;
            case "python" :
                fileName += ".py";
                break;
            default:
                throw new WrongLangTypeException("현재 지원하지 않는 유형의 언어입니다.");
        }

        String[] printSourceCodeCommand = {"sh", "-c", "cat /usr/src/"+fileName};// 파일 코드 출력 명령어
        StringBuilder standardOutputLogs = new StringBuilder();// 결과 출력
        StringBuilder standardErrorLogs = new StringBuilder();// 에러 출력
        StringBuilder exceptions = new StringBuilder();// 예외 출력

        try {
            ExecCreateCmdResponse runResponse = dockerClient.execCreateCmd(member.getDocker().getContainerId())
                    .withAttachStdout(true)
                    .withAttachStderr(true)
                    .withAttachStdin(true) // stdin 활성화
                    .withTty(true) // TTY 활성화
                    .withCmd(printSourceCodeCommand)
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
            if (!runSuccess) {
                codeResult.setExceptions("Code execution failed.");
            }
            codeResult.setStandardOutput(standardOutputLogs.toString());
            codeResult.setStandardError(standardErrorLogs.toString());
            codeResult.setExceptions(exceptions.toString());
        } catch (InterruptedException e) {
            throw new CustomInterruptedException("인터럽트 예외 발생");
        }

        return codeResult;//실행 결과를 Standard Stream으로 출력
    }


    // 사용자 요청에 맞는 이미지를 가져옵니다.
    // 관리자 기능에 추가하여 더 다양한 기능을 추가할 수 있을듯?
    public void pullDockerImage(RequestImage image) {
        try {
            dockerClient.pullImageCmd(image.getRepository())//사용하려는 이미지 이름인듯
                    .withTag(image.getTage())//버전같은 정보
                    .exec(new PullImageResultCallback())
                    .awaitCompletion(30, TimeUnit.SECONDS );
        } catch (InterruptedException e) {
            throw new CustomInterruptedException("인터럽트 예외가 발생했습니다.");
        }
    }

    // 자바 이미지 요청
    public void pullDockerJavaImage() {
        try {
            dockerClient.pullImageCmd("openjdk")//사용하려는 이미지 이름인듯
                    .withTag("22-ea-16-jdk")//버전같은 정보
                    .exec(new PullImageResultCallback())
                    .awaitCompletion(30, TimeUnit.SECONDS );
        } catch (InterruptedException e) {
            throw new CustomInterruptedException("인터럽트 예외가 발생했습니다.");
        }
    }

    // 파이썬 이미지 가져오기
    public void pullDockerPythonImage() throws InterruptedException {
        try {
            dockerClient.pullImageCmd("python")//사용하려는 이미지 이름인듯
                    .withTag("3.10.13-bookworm")//버전같은 정보
                    .exec(new PullImageResultCallback())
                    .awaitCompletion(30, TimeUnit.SECONDS );
        } catch (InterruptedException e) {
            throw new CustomInterruptedException("인터럽트 예외가 발생했습니다.");
        }
    }
}
