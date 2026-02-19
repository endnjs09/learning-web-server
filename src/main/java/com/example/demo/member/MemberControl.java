package com.example.demo.member;

import com.example.demo.board.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String join(@RequestParam("name") String name,
                       @RequestParam("password") String password,
                       Model model,
                       RedirectAttributes re){

        if (name == null || name.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {

            // 다시 가입 폼으로 보내면서 에러 메시지 전달
            re.addFlashAttribute("joinError", "아이디와 비밀번호를 정확히 입력해주세요.");
            return "redirect:/join-form";
        }

        // 중복 검사
        try {
            memberService.join(name, password);
            // 리다이렉트 할 때 이름을 같이 실어 보냄
            re.addAttribute("name", name);
            return "redirect:/welcome"; // 주소창을 /welcome으로 강제 이동
        } catch (IllegalArgumentException e) {
            re.addFlashAttribute("joinError", e.getMessage());
            return "redirect:/join-form";
        }
    }


    @GetMapping("/welcome")
    public String welcomePage(@RequestParam("name") String name, Model model) {
        model.addAttribute("username", name);
        return "welcome";
    }

    @PostMapping("/login")  // POST방식은 데이터를 주소창에 노출시키지 않음
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session, RedirectAttributes redirectAttributes) {

        Member loginMember = memberService.login(username, password);

        if (loginMember == null) {
            // loginError라는 이름표를 붙여서 메시지를 담아 보냄
            redirectAttributes.addFlashAttribute("loginError", "아이디 또는 비밀번호가 틀렸습니다.");
            return "redirect:/list";  // 실패 시 login-form으로 다시 돌아감
        }

        session.setAttribute("loginMember", loginMember);
        // 일치하면 컨트롤러가 세션에 이 정보를 저장함 (로그인 유지)
        // 세션이 없으면 사용자가 다른 페이지로 이동할 때 로그인 정보를 까먹어버림

        return "redirect:/list";    // 성공시 list로
    }


    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 날리기
        return "redirect:/list";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "register";
    }
}
