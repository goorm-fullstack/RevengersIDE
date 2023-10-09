package Revengers.IDE.member.model;

import Revengers.IDE.docker.model.Docker;
import Revengers.IDE.member.dto.response.LoginResponse;
import Revengers.IDE.member.dto.response.MemberResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(nullable = false)
    private LocalDateTime createMemberDate;     //회원가입 일
    
    // 일단은 한 사용자가 하나의 도커 컨테이너를 가리키도록 한다.
    @OneToMany
    private List<Docker> docker;

    public LoginResponse toLoginResponse() {
        return new LoginResponse(this);
    }

    public MemberResponse toMemberResponse() {
        return new MemberResponse(this);
    }

}
