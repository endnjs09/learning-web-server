package com.example.demo.member;

import com.example.demo.member.dto.MemberRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // JSON을 리턴하는 컨트롤러
@RequestMapping("/api/member") // 주소를 /api로 시작하게 해서 구분
@RequiredArgsConstructor
public class MemberAPIControl {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberRequest req) {
        // @RequestBody: JSON 데이터를 자바 객체로 반환

        try {
            memberService.join(req.getName(), req.getPassword());

            // 성공시 메시지와 데이터를 JSON으로 리턴
            return ResponseEntity.ok("가입이 성공적으로 완료되었습니다.");
        }
        catch (IllegalArgumentException e) {
            // 실패시 400 에러와 에러 메시지 리턴
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberRequest request, HttpSession session) {
        Member loginMember = memberService.login(request.getName(), request.getPassword());

        if (loginMember == null) {
            // 로그인 실패 시 401(미인증) 상태와 에러 메시지 전송
            return ResponseEntity.status(401).body("아이디 또는 비밀번호가 틀렸습니다.");
        }

        // 로그인 성공 시 세션에 저장 (서버가 기억하게 함)
        session.setAttribute("loginMember", loginMember);

        return ResponseEntity.ok("로그인 성공!");
    }

}
