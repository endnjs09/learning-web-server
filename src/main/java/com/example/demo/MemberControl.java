package com.example.demo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor    // final 붙은 애들용 생성자를 대신 만들어줌
public class MemberControl {

    private final MemberService memberService;
    private final BoardService boardService;

    MemberRepository memberRepository;

    // HTML의 action="/join"과 매칭됨
    @GetMapping("/join")
    public String join(@RequestParam("name") String name, @RequestParam("password") String password){
        Member newMember = new Member();
        newMember.setUsername(name);
        newMember.setPassword(password);
        newMember.setPoints(0);
        newMember.setGrade("BRONZE");

        memberRepository.save(newMember);
        return name + "님, 가입을 축하드립니다.";
    }

    @GetMapping("/add-point")   // xxx.com/add-point으로 접속하면 아래의 코드를 실행함
    public String addPoint(@RequestParam("id") Long id) {   // 주소에서 id 값을 가져와서 id 변수에 넣음
        memberService.givePoint(id);
        return id + "번 회원의 포인트를 올렸습니다.";
    }

    @GetMapping("/write")
    public String write(@RequestParam("id") Long id, @RequestParam("title") String title) {
        boardService.writePost(id, title, "내용은 생략!");
        return title + " 글이 작성되었고, 포인트가 적립되었습니다!";
    }


    @PostMapping("/login")  // POST방식은 데이터를 주소창에 노출시키지 않음
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        Member loginMember = memberService.login(username, password);

        if (loginMember == null) {
            return "아이디나 비밀번호를 확인하세요.";
        }

        session.setAttribute("loginMember", loginMember);
        return username + "님 환영합니다.";
    }
}
