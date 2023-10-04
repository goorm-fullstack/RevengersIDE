package Revengers.IDE.member.model;

import Revengers.IDE.member.dto.response.LoginResponse;
import Revengers.IDE.member.dto.response.MemberResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberId;        // 로그인 ID

    @Column(nullable = false)
    private String password;        // 로그인 PW

    @Column(nullable = false)
    private String memberName;      // 회원 이름

    @Column(nullable = false)
    private String email;           // 회원 이메일

    @Column(nullable = false)
    private MemberRole role;        // 권한: MEMBER 또는 ADMIN

    public LoginResponse toLoginResponse() {
        return new LoginResponse(this);
    }

    public MemberResponse toMemberResponse() {
        return new MemberResponse(this);
    }
    
}
