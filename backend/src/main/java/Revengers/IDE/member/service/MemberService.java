package Revengers.IDE.member.service;

import Revengers.IDE.member.dto.request.RequestMemberDto;
import Revengers.IDE.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private MemberRepository memberRepository;

    public RequestMemberDto save(RequestMemberDto memberDto) {
        memberRepository.save(memberDto.toEntity());
        return memberDto;
    }
}
