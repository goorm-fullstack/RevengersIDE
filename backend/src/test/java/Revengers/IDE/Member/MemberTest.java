package Revengers.IDE.Member;

import Revengers.IDE.member.controller.MemberController;
import Revengers.IDE.member.dto.request.SignUpRequest;

import Revengers.IDE.member.model.Member;
import Revengers.IDE.member.repository.MemberRepository;
import Revengers.IDE.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        Optional<Member> oldMemberOptional = memberRepository.findByMemberId("testid");

        if (oldMemberOptional.isPresent()) {
            Member member = oldMemberOptional.get();

            // 새로운 비밀번호
            String newPassword = "newtestpw";

            // 비밀번호 업데이트
            Member updatedMember = memberService.updatePassword(member.getMemberId(), newPassword);
            System.out.println("updatedMemberPassword = " + updatedMember.getPassword());
//            assertNotNull(updatedMember);
//
//            // 업데이트된 회원 정보 확인
//            assertEquals(member.getMemberId(), updatedMember.getMemberId());
//            assertNotEquals(member.getPassword(), updatedMember.getPassword());
        } else {
            // 회원을 찾을 수 없는 경우에 대한 처리
            fail("회원을 찾을 수 없습니다.");
        }

//        Member member = memberService.getMemberByMemberIdAndMemberNameAndEmail("testid", "테스트", "test@test.com");
//        Member member = memberService.getMemberByMemberNameAndEmail("테스트2", "test2@test.com");
//        System.out.println(member.getPassword());
//        System.out.println(member.getMemberId());
    }
}
