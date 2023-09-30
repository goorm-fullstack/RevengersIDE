package Revengers.IDE.docker.model;

import lombok.Data;

/**
 * 관리자 요청에 맞는 이미지를 요청할 수 있도록 요청에 대한 데이터를 담습니다,
 * 이미지 이름과 태그를 지정해주세요. -> 도커 허브에서 확인가능합니다.
 */
@Data
public class RequestImage {
    private String repository;//요청 이미지 리포지토리
    private String tage;//요청 이미지 태그
}
