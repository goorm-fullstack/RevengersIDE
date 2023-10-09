package Revengers.IDE.member.repository;

import Revengers.IDE.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 데이터 존재 여부 확인: 중복 가입 방지
    boolean existsByMemberId(String memberId);
    boolean existsByEmail(String email);

    Optional<Member> findByMemberId(String memberId);

    //이름, Email로 아이디 찾기
    Optional<Member> findByMemberNameAndEmail(String memberName, String email);

    //Id, 이름, Email로 비밀번호 찾기
    Optional<Member> findByMemberIdAndMemberNameAndEmail(String memberId, String memberName, String email);

    List<Member> findByCreateMemberDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

}
