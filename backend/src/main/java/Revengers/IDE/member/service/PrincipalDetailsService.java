package Revengers.IDE.member.service;

import Revengers.IDE.member.model.Member;
import Revengers.IDE.member.model.MemberRole;
import Revengers.IDE.member.model.PrincipalDetails;
import Revengers.IDE.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private Map<String, Member> roles = new HashMap<>();

    @PostConstruct
    public void init() {
        roles.put("admin", new Member("admin", "admin1234", "관리자", "goorm@goorm.com", MemberRole.ADMIN));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(username).orElseThrow(() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."));
        return new PrincipalDetails(member);
    }
}
