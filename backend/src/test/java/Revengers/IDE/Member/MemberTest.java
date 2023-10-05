package Revengers.IDE.Member;

import Revengers.IDE.member.controller.MemberController;
import Revengers.IDE.member.dto.request.SignUpRequest;

import Revengers.IDE.member.repository.MemberRepository;
import Revengers.IDE.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
                .memberId("testid2")
                .password("testpw2")
                .memberName("테스트2")
                .email("test2@test.com")
                .build();

        memberService.singup(signup);
//        memberService.getMemberByMemberId("testid")

//        Member member = memberService.getMemberByMemberIdAndMemberNameAndEmail("testid", "테스트", "test@test.com");
//        Member member = memberService.getMemberByMemberNameAndEmail("테스트2", "test2@test.com");
//        System.out.println(member.getPassword());
//        System.out.println(member.getMemberId());
    }
}
