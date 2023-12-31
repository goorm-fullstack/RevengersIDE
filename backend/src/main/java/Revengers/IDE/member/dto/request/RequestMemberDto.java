package Revengers.IDE.member.dto.request;

import Revengers.IDE.member.model.Member;
import lombok.Data;

import java.util.ArrayList;

@Data
public class RequestMemberDto {
    private String username;
    private String password;

    public Member toEntity() {
        return Member.builder()
                .memberName(username)
                .password(password)
                .docker(new ArrayList<>())
                .build();
    }
}
