package com.example.demo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // RestController는 html 파일 이름이 아니라 무조건 데이터(글자, JSON) 리턴
@RequiredArgsConstructor    // final 붙은 애들용 생성자를 대신 만들어줌
public class MemberControl {

    private final MemberService memberService;
    private final BoardService boardService;
    private final MemberRepository memberRepository;

    // HTML의 action="/join"과 매칭됨
    @PostMapping("/join")
    @ResponseBody   // 브라우저에 글자 그대로 띄우고 싶을 때 추가
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
    @ResponseBody   // 브라우저에 글자 그대로 띄우고 싶을 때 추가
    public String addPoint(@RequestParam("id") Long id) {   // 주소에서 id 값을 가져와서 id 변수에 넣음
        memberService.givePoint(id);
        return id + "번 회원의 포인트를 올렸습니다.";
    }

    @GetMapping("/write")
    @ResponseBody   // 브라우저에 글자 그대로 띄우고 싶을 때 추가
    public String write(@RequestParam("id") Long id, @RequestParam("title") String title) {
        boardService.writePost(id, title, "내용은 생략!");
        return title + " 글이 작성되었고, 포인트가 적립되었습니다!";
    }


    @PostMapping("/login")  // POST방식은 데이터를 주소창에 노출시키지 않음
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session, RedirectAttributes redirectAttributes) {

        Member loginMember = memberService.login(username, password);

        if (loginMember == null) {
            // loginError라는 이름표를 붙여서 메시지를 담아 보냄
            redirectAttributes.addFlashAttribute("loginError", "아이디 또는 비밀번호가 틀렸습니다.");
            return "redirect:/login-form";  // 실패 시 login-form으로 다시 돌아감
        }

        session.setAttribute("loginMember", loginMember);
        // 일치하면 컨트롤러가 세션에 이 정보를 저장함 (로그인 유지)
        // 세션이 없으면 사용자가 다른 페이지로 이동할 때 로그인 정보를 까먹어버림.

        return "redirect:/list";    // 성공시 list로
    }

    @GetMapping("/login-form") // 브라우저 주소창에 칠 주소
    public String loginForm() {
        return "login"; // templates/login.html 파일을 찾아감
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "register";
    }
}
