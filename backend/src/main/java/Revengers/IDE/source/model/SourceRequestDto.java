package Revengers.IDE.source.model;

import lombok.Data;

@Data
public class SourceRequestDto {
    private Long id;
    private String source;//소스 코드
    private String languageType;//언어 타입 -> Java, Python
}
