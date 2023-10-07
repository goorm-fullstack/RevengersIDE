package Revengers.IDE.docker.exception;

// 일반 예외를 런타임 예외로 바꾸자
public class CustomInterruptedException extends RuntimeException{
    public CustomInterruptedException() {
        super();
    }

    public CustomInterruptedException(String message) {
        super(message);
    }
}
