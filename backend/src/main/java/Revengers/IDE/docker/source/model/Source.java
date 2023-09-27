package Revengers.IDE.docker.source.model;

import Revengers.IDE.docker.source.type.LanguageType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class Source {
    private Long id;
    private String source;//소스 코드
    private String languageType;//언어 타입 -> Java, Python
    private String fileName;//파일 이름
}
