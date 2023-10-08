package Revengers.IDE.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

    private String memberId;        // 로그인 ID
    private String password;        // 로그인 PW

}
