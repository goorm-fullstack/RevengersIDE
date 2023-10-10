package Revengers.IDE.member.dto.response;

import Revengers.IDE.member.model.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginResponse {

    private String memberId;        // 로그인 ID
    private String memberName;      // 회원 이름

    public LoginResponse(Member member) {
        this.memberId = member.getMemberId();
        this.memberName = member.getMemberName();
    }

}
