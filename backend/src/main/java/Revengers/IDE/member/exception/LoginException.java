package Revengers.IDE.member.exception;

//로그인 관련 예외
public class LoginException extends RuntimeException{
    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }
}
