package Revengers.IDE.member.controller;

import Revengers.IDE.member.dto.request.LoginRequest;
import Revengers.IDE.member.dto.request.SignUpRequest;
import Revengers.IDE.member.dto.response.MemberResponse;
import Revengers.IDE.member.model.Member;
import Revengers.IDE.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인 상태일 때 index 화면 memberName 출력용
     *
     * @param auth 권한
     * @return 회원 이름
     */
    @GetMapping(value = {"", "/"})
    public String getMemberName(Authentication auth) {
        String memberName = null;

        if (auth != null) {
            Member loginMember = memberService.getLoginByMemberId(auth.getName());
            if (loginMember != null) {
                memberName = loginMember.getMemberName();
            }
        }
        return memberName;
    }

    /**
     * 회원가입
     *
     * @param sign SignUpRequest
     * @param bindingResult 검증 결과 기록
     * @return 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<Object> SignUp(@Validated @RequestBody SignUpRequest sign, BindingResult bindingResult) {
        // memberId 중복 검사
        if (memberService.checkMemberIdDuplicate(sign.getMemberId())) {
            bindingResult.addError(new FieldError("sign", "memberId", "회원 ID 중복"));
        }

        // email 중복 검사
        if (memberService.checkEmailDuplicate(sign.getEmail())) {
            bindingResult.addError(new FieldError("sign", "email", "이메일 중복"));
        }

        // password 확인 검사
        if (!sign.getPassword().equals(sign.getPasswordCheck())) {
            bindingResult.addError(new FieldError("sign", "passwordCheck", "비밀번호 불일치"));
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors().toString());
        }

        memberService.singup(sign);
        return ResponseEntity.ok().build();
    }

    /**
     * 로그인
     *
     * @param request LoginRequest
     * @return 로그인
     */
    @GetMapping("/login")
    public String loginPage(LoginRequest request) {
        memberService.login(request);
        return "login";
    }

    /**
     * 회원 정보 출력
     *
     * @param auth 권한
     * @return 회원 정보
     */
    @GetMapping("/myaccount")
    public ResponseEntity<MemberResponse> getMyAccount(Authentication auth) {
        Member loginMember = memberService.getLoginByMemberId(auth.getName());

        if (loginMember == null) { // 권한 확인
            return ResponseEntity.badRequest().build();
        }

        MemberResponse response = memberService.getMemberByMemberId(loginMember).toMemberResponse();
        return ResponseEntity.ok(response);
    }

    /**
     * 전체 회원 조회
     *
     * @return 전체 회원 목록
     */
    @GetMapping("/all")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }


    /**
     * 아이디 찾기
     *
     * @return 이름, 이메일이 일치하는 아이디 찾기
     */
    @PostMapping("/findId")
    public ResponseEntity<Object> getMemberId(@RequestBody Map<String, String> requestBody) {
        String memberName = requestBody.get("memberName");
        String email = requestBody.get("email");

        Member member = memberService.getMemberByMemberNameAndEmail(memberName, email);

        return ResponseEntity.ok(member);
    }

    /**
     * 비밀번호 찾기
     *
     * @return 이름, 이메일, 아이디가 일치하는 비밀번호 찾기
     */
    @PostMapping("/findPassword")
    public ResponseEntity<Object> getMemberPassword(@RequestBody Map<String, String> requestBody) {
        String memberId = requestBody.get("memberId");
        String memberName = requestBody.get("memberName");
        String email = requestBody.get("email");

        Member member = memberService.getMemberByMemberIdAndMemberNameAndEmail(memberId, memberName, email);

        return ResponseEntity.ok(member);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody Map<String, String> requestBody) {
        String memberId = requestBody.get("memberId");
        String newPassword = requestBody.get("newPassword");

        Member newMember = memberService.updatePassword(memberId, newPassword);

        return ResponseEntity.ok(newMember);
    }
}
