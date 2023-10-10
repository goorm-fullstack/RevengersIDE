package Revengers.IDE.member.controller;

import Revengers.IDE.member.dto.request.LoginRequest;
import Revengers.IDE.member.dto.request.MemberRequest;
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
     * @return 회원 이름(회원 아이디)
     */
    @GetMapping(value = {"", "/"})
    public String getMemberName(Authentication auth) {
        String memberName = null;

        if (auth != null) {
            Member loginMember = memberService.getLoginByMemberId(auth.getName());
            if (loginMember != null) {
                memberName = loginMember.getMemberName() + "(" + loginMember.getMemberId() + ")";
            }
        }
        System.out.println(memberName);
        return memberName;
    }

    /**
     * 회원가입
     *
     * @param request SignUpRequest
     * @param bindingResult 검증 결과 기록
     * @return 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<Object> SignUp(@Validated @RequestBody MemberRequest request, BindingResult bindingResult) {

        // memberId 중복 검사
        ResponseEntity<Object> BAD_REQUEST = checkMemberInfo(request, bindingResult, "signup");
        if (BAD_REQUEST != null) return BAD_REQUEST;

        memberService.singup(request);
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
    public ResponseEntity<Object> getMyAccount(Authentication auth) {
        Member loginMember = memberService.getLoginByMemberId(auth.getName());

        if (loginMember == null) { // 권한 확인
            return ResponseEntity.badRequest().build();
        }

        MemberResponse response = memberService.getMemberByMemberId(loginMember).toMemberResponse();
        return ResponseEntity.ok(response);
    }

    /**
     * 회원 정보 업데이트
     * @param request
     * @param bindingResult
     * @return
     */
    @PostMapping("/myaccount")
    public ResponseEntity<Object> updateMyAccount(@Validated @RequestBody MemberRequest request, BindingResult bindingResult) {

        // 중복 검사
        ResponseEntity<Object> BAD_REQUEST = checkMemberInfo(request, bindingResult, "update");
        if (BAD_REQUEST != null) return BAD_REQUEST;

        memberService.updateMyAccount(request);
        return ResponseEntity.ok().build();
    }

    /**
     * 중복 검사
     * @param request
     * @param bindingResult
     * @return
     */
    private ResponseEntity<Object> checkMemberInfo(MemberRequest request, BindingResult bindingResult, String action) {

        if(action.equals("signup")) {
            // memberId 중복 검사
            if (memberService.checkMemberIdDuplicate(request.getMemberId())) {
                bindingResult.addError(new FieldError("checkMemberInfo", "memberId", "회원 ID 중복"));
            }

            // email 중복 검사
            if (memberService.checkEmailDuplicate(request.getEmail())) {
                bindingResult.addError(new FieldError("checkMemberInfo", "email", "이메일 중복"));
            }
        }

        // password 확인 검사
        if (!request.getPassword().equals(request.getPasswordCheck())) {
            bindingResult.addError(new FieldError("checkMemberInfo", "passwordCheck", "비밀번호 불일치"));
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors().toString());
        }

        return null;
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

    /**
     *
     * @param requestBody
     * @return
     */
    @PostMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody Map<String, String> requestBody) {
        String memberId = requestBody.get("memberId");
        String newPassword = requestBody.get("newPassword");

        Member newMember = memberService.updatePassword(memberId, newPassword);

        return ResponseEntity.ok(newMember);
    }

    //오늘 가입한 인원
    @GetMapping("/todayMember")
    public ResponseEntity<List<Member>> getTodayMembers() {

        List<Member> todayMembers = memberService.getTodayMembers();

        return ResponseEntity.ok(todayMembers);
    }

    //어제 가입한 인원
    @GetMapping("/yesterdayMember")
    public ResponseEntity<List<Member>> getYesterdayMembers() {

        List<Member> todayMembers = memberService.getYesterdayMembers();

        return ResponseEntity.ok(todayMembers);
    }

    //회원 정보(이메일, 비밀번호) 수정
    @PostMapping("/updateMember")
    public ResponseEntity<Object> updateMember(@RequestBody Map<String, String> requestBody) {
        String memberId = requestBody.get("memberId");
        String newPassword = requestBody.get("newPassword");
        String email = requestBody.get("email");

        Member newMember = memberService.updateMember(memberId, newPassword, email);

        return ResponseEntity.ok(newMember);
    }

    //memberId로 회원 정보 조회
    @PostMapping("/findById")
    public ResponseEntity<Member> findById(@RequestBody Map<String, String> requestBody){
        String memberId = requestBody.get("memberId");
        Member member = memberService.findByMemberId(memberId);

        return ResponseEntity.ok(member);
    }

    //회원 정보 삭제
    @DeleteMapping("/deleteId/{memberId}")
    public void deleteMember(@PathVariable String memberId) {
        memberService.deleteById(memberId);
    }
}
