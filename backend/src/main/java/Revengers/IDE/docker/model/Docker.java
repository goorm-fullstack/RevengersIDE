package Revengers.IDE.docker.model;

import Revengers.IDE.member.model.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Docker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "docker_id")
    private long id;
    private String containerId;//컨테이너 이름
    private String langType;

    @ManyToOne
    private Member member;//사용자

    @Builder
    public Docker(String containerId, String langType) {
        this.containerId = containerId;
        this.langType = langType;
    }
}
