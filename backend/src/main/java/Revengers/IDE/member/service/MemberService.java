package Revengers.IDE.member.service;

import Revengers.IDE.member.dto.request.LoginRequest;
import Revengers.IDE.member.dto.request.MemberRequest;
import Revengers.IDE.member.exception.LoginException;
import Revengers.IDE.member.model.Member;
import Revengers.IDE.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public void singup(MemberRequest request) {
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

    /**
     * 이름, Email로 아이디 찾기
     * @param memberName 회원 이름
     * @param email 회원 이메일
     * @return
     */
    public Member getMemberByMemberNameAndEmail(String memberName, String email){
        Optional<Member> member = memberRepository.findByMemberNameAndEmail(memberName, email);

        return member.orElse(null);
    }

    /**
     * Id, 이름, Email로 비밀번호 찾기
     * @param memberId 회원 아이디
     * @param memberName 회원 이름
     * @param email 회원 이메일
     * @return
     */
    public Member getMemberByMemberIdAndMemberNameAndEmail(String memberId, String memberName, String email){
        Optional<Member> member = memberRepository.findByMemberIdAndMemberNameAndEmail(memberId, memberName, email);

        return member.orElse(null);
    }

    /**
     * 회원 비밀번호 변경
     * @param memberId 회원 아이디
     * @param newPassword 새로운 비밀번호
     * @return 바뀐 정보로 저장
     */
    public Member updatePassword(String memberId, String newPassword) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);

        Member member = optionalMember.get();
        member.setPassword(encoder.encode(newPassword));

        return memberRepository.save(member);
    }

    //오늘 회원가입 멤버 구하기
    public List<Member> getTodayMembers() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);
        return memberRepository.findByCreateMemberDateBetween(startOfDay, endOfDay);
    }

    //어제 가입한 멤버 구하기
    public List<Member> getYesterdayMembers() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime yesterdayStartOfDay = yesterday.atStartOfDay();
        LocalDateTime yesterdayEndOfDay = yesterday.atTime(23, 59, 59);

        return memberRepository.findByCreateMemberDateBetween(yesterdayStartOfDay, yesterdayEndOfDay);
    }

    //회원 정보 수정(비밀번호, 이메일 변경)
    public Member updateMember(String memberId, String newPassword, String email) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);

        Member member = optionalMember.get();
        member.setPassword(encoder.encode(newPassword));
        member.setEmail(email);

        return memberRepository.save(member);
    }

    //memberId로 회원 정보 찾기
    public Member findByMemberId(String memberId){
        Optional<Member> member = memberRepository.findByMemberId(memberId);

        return member.orElse(null);
    }

    //회원 정보 삭제
    public void deleteById(String memberId) {
        memberRepository.deleteByMemberId(memberId);
    }

    /**
     * 사용자 페이지 회원 정보 업데이트
     * @param request
     */
    public void updateMyAccount(MemberRequest request) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(request.getMemberId());

        // 일치하는 id 검색, 없으면 종료
        if (optionalMember.isEmpty()) return;

        Member member = optionalMember.get();
        // 비밀번호 일치하지 않는 경우
        if (!encoder.matches(request.getPassword(), member.getPassword())) {
            member.setPassword(encoder.encode(request.getPassword()));
        }
        member.setEmail(request.getEmail());

        memberRepository.save(member);

    }
}
