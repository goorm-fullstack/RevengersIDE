package Revengers.IDE.docker.model;

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
    private long id;
    private String containerId;//컨테이너 이름
    private String langType;

    /*
    @OneToMany
    private Member member;//사용자
    */

    @Builder
    public Docker(String containerId, String langType) {
        this.containerId = containerId;
        this.langType = langType;
    }
}
