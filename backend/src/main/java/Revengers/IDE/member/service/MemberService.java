package Revengers.IDE.member.service;

import Revengers.IDE.member.dto.request.LoginRequest;
import Revengers.IDE.member.dto.request.SignUpRequest;
import Revengers.IDE.member.model.Member;
import Revengers.IDE.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (!member.getPassword().equals(request.getPassword())) return null;

        return member;
    }


    /**
     * 인증, 인가 용도 memberId 검색
     */
    public Member getLoginByMemberId(String memberId) {
        if(memberId == null) return null;

        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if(optionalMember.isEmpty()) return null;

        return optionalMember.get();
    }

}
