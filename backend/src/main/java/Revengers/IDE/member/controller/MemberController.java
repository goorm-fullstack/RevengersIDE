package Revengers.IDE.member.controller;

import Revengers.IDE.member.dto.request.LoginRequest;
import Revengers.IDE.member.dto.request.SignUpRequest;
import Revengers.IDE.member.dto.response.MemberResponse;
import Revengers.IDE.member.model.Member;
import Revengers.IDE.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인 상태일 때 index 화면 memberName 출력용
     * @param auth
     * @return
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
     * @param sign
     * @param bindingResult
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<Object> SignUp(@Validated @RequestBody SignUpRequest sign, BindingResult bindingResult) {
        // memberId 중복 검사
//        if (memberService.checkMemberIdDuplicate(sign.getMemberId())) {
//            bindingResult.addError(new FieldError("sign", "memberId", "회원 ID 중복"));
//        }
//
//        // email 중복 검사
//        if (memberService.checkEmailDuplicate(sign.getEmail())) {
//            bindingResult.addError(new FieldError("sign", "email", "이메일 중복"));
//        }
//
//        // password 확인 검사
//        if (!sign.getPassword().equals(sign.getPasswordCheck())) {
//            bindingResult.addError(new FieldError("sign", "passwordCheck", "비밀번호 불일치"));
//        }
//
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.badRequest().body(bindingResult);
//        }

        memberService.singup(sign);
        return ResponseEntity.ok().build();
    }

    /**
     * 회원 정보 출력
     * @param memberId
     * @param auth
     * @param bindingResult
     * @return
     */
    @PostMapping("/myaccount/{memberId}")
    public ResponseEntity<MemberResponse> getMyAccount(@PathVariable String memberId, Authentication auth, BindingResult bindingResult) {
        Member loginMember = memberService.getLoginByMemberId(auth.getName());

        if (loginMember == null || bindingResult.hasErrors()) {
//            return new ResponseEntity(new ErrorResponse("404", "권한 필요"), HttpStatus.NOT_FOUND);
            return ResponseEntity.badRequest().body((MemberResponse) bindingResult);
        }

        return ResponseEntity.ok().build();
    }

}
