package Revengers.IDE.member.service;

import Revengers.IDE.member.dto.request.LoginRequest;
import Revengers.IDE.member.dto.request.SignUpRequest;
import Revengers.IDE.member.exception.LoginException;
import Revengers.IDE.member.model.Member;
import Revengers.IDE.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;    // spring security

    /**
     * 회원가입 시 memberId 중복 체크
     * - 중복 == true
     */
    public boolean checkMemberIdDuplicate(String memberId) {
        return memberRepository.existsByMemberId(memberId);
    }

    /**
     * 회원가입 시 email 중복 체크
     * - 중복 == true
     */
    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    /**
     * 회원가입: 입력받은 값 + 비밀번호 암호화
     */
    public void singup(SignUpRequest request) {
        memberRepository.save(request.toEntity(encoder.encode(request.getPassword())));
    }

    /**
     * 로그인
     * - ID, PW 일치 시 Member return
     * - 존재하지 않는 ID 또는 PW 불일치 시 null return
     */
    public Member login(LoginRequest request) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(request.getMemberId());

        // 일치하는 id 검색, 없으면 null
        if (optionalMember.isEmpty()) return null;

        // password 불일치 시 null
        Member member = optionalMember.get();
        if (!encoder.matches(request.getPassword(), member.getPassword())) {
            System.out.println("실패");
            throw new LoginException("로그인에 실패했습니다.");
        }

        System.out.println(member.toString());
        return member;
    }


    /**
     * 인증, 인가 용도 memberId 검색
     */
    public Member getLoginByMemberId(String memberId) {
        if(memberId == null) return null;

        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        return optionalMember.orElse(null);

    }

    public Member getMemberByMemberId(Member member) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(member.getMemberId());

        // 일치하는 id 검색, 없으면 null
        return optionalMember.orElse(null);

    }

    /**
     * 전체 회원 조회
     * @return 전체 회원 목록
     */
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }


    //이름, Email로 아이디 찾기
    public Member getMemberByMemberNameAndEmail(String memberName, String email){
        Optional<Member> member = memberRepository.findByMemberNameAndEmail(memberName, email);

        return member.orElse(null);
    }

    //Id, 이름, Email로 비밀번호 찾기
    public Member getMemberByMemberIdAndMemberNameAndEmail(String memberId, String memberName, String email){
        Optional<Member> member = memberRepository.findByMemberIdAndMemberNameAndEmail(memberId, memberName, email);

        return member.orElse(null);
    }

    //회원 비밀번호 변경
    public Member updatePassword(String memberId, String newPassword) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);

        Member member = optionalMember.get();
        member.setPassword(encoder.encode(newPassword));

        return memberRepository.save(member);
    }

}
