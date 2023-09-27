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
    private String containerId;//컨테이너 아이디
    private String imageName;//이미지 이름

    /*
    @OneToMany
    private Member member;//사용자
    */

    @Builder
    public Docker(String containerId, String imageName) {
        this.containerId = containerId;
        this.imageName = imageName;
    }
}
