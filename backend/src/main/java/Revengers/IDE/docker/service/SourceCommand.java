package Revengers.IDE.docker.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SourceCommand {
    private Long sourceId;
    private String source;//소스 코드
    private String lType;//언어 타입 -> Java, Python
}
