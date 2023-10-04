package Revengers.IDE.docker.exception;

// 잘못된 유형의 언어 타입을 입력하는 경우에 발생
public class WrongLangTypeException extends RuntimeException{
    public WrongLangTypeException() {
        super();
    }

    public WrongLangTypeException(String message) {
        super(message);
    }
}
