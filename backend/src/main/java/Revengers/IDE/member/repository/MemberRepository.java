package Revengers.IDE.member.repository;

import Revengers.IDE.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 데이터 존재 여부 확인: 중복 가입 방지
    boolean existsByMemberId(String memberId);
    boolean existsByEmail(String email);

    Optional<Member> findByMemberId(String memberId);
}
