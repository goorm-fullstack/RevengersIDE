package Revengers.IDE.Member;

import Revengers.IDE.member.controller.MemberController;
import Revengers.IDE.member.dto.request.SignUpRequest;
import Revengers.IDE.member.model.MemberRole;
import Revengers.IDE.member.repository.MemberRepository;
import Revengers.IDE.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@SpringBootTest
@Transactional
public class MemberTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberController memberController;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    public void memberTest() {
        SignUpRequest signup = SignUpRequest.builder()
                .memberId("testid")
                .password("testpw")
                .memberName("테스트")
                .email("test@test.com")
                .build();

        memberService.singup(signup);

    }
}
