package Revengers.IDE.docker.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestCompileDto {

    private String source;
    private String fileName;
    private String language;
}
