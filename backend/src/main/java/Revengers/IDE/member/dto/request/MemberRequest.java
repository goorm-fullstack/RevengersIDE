package Revengers.IDE.member.dto.request;

import Revengers.IDE.member.model.Member;
import Revengers.IDE.member.model.MemberRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequest {

    @NotBlank(message = "ID를 입력해주세요.")
    private String memberId;        // 로그인 ID

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;        // 로그인 PW
    private String passwordCheck;   // 비밀번호 확인: 검증은 controller

    @NotBlank(message = "이름을 입력해주세요.")
    private String memberName;      // 회원 이름

    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;           // 회원 이메일

    private LocalDateTime createMemberDate = LocalDateTime.now();         //회원가입 일


    @Builder(toBuilder = true)
    public MemberRequest(String memberId, String password, String passwordCheck, String memberName, String email, LocalDateTime createMemberDate) {
        this.memberId = memberId;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.memberName = memberName;
        this.email = email;
        this.createMemberDate = createMemberDate;
    }

    public Member toEntity(String encodedPassword) {  // 비밀번호 암호화 적용
        return Member.builder()
                .memberId(this.memberId)
                .password(encodedPassword)
                .memberName(this.memberName)
                .email(this.email)
                .createMemberDate(this.createMemberDate)
                .role(MemberRole.MEMBER)
                .build();
    }

}
