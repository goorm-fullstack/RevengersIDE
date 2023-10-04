package Revengers.IDE.member.model;

import Revengers.IDE.docker.model.Docker;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    // 일단은 한 사용자가 하나의 도커 컨테이너를 가리키도록 한다.
    @OneToOne
    private Docker docker;


    @Builder
    public Member(Long id, String username, String password, Docker docker) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.docker = docker;
    }
}
