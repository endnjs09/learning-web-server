package com.example.demo.board;

import com.example.demo.member.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class BoardControl {

    private final BoardService boardService; // 서비스 주입

    @PostMapping("/write")
    public String write(HttpSession session,
                        @RequestParam("title") String title,
                        @RequestParam("content") String content,
                        RedirectAttributes re) { // (1) RedirectAttributes 추가

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            // 일회성 메시지(FlashAttribute)를 담습니다.
            // 리다이렉트 직후 한 번만 유효하고 사라집니다.
            re.addFlashAttribute("loginMessage", "로그인이 필요한 서비스입니다!");
            return "redirect:/list";
        }

        boardService.writePost(loginMember.getId(), title, content);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        // boardService가 글을 다 긁어옴
        Page<Board> paging = boardService.getList(page);

        // boards라는 이름으로 데이터를 HTML에 전달
        model.addAttribute("paging", paging);

        return "list"; // list.html 파일로 이동
    }

    @GetMapping("/view")
    public String viewPost(@RequestParam("id") Long id, Model model) {
        // boardService가 해당 id에 해당하는 글을 긁어옴
        Board post = boardService.findPostById(id);

        // Model에 post라는 이름으로 담음
        model.addAttribute("post", post);

        return "view";
    }

    // 수정 페이지
    @GetMapping("/edit")
    public String editForm(@RequestParam("id") Long id, Model model) {
        Board post = boardService.findPostById(id); // 기존 글 정보 가져오기
        model.addAttribute("post", post);
        return "update"; // update.html로 이동
    }

    // 실제 수정 처리
    @PostMapping("/update")
    public String updatePost(@RequestParam("id") Long id,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content,
                             HttpSession session) {

        // 세션 검사
        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember != null) {
            boardService.updatePost(id, title, content, loginMember.getId());
        }

        return "redirect:/view?id=" + id; // 수정 후 다시 해당 글로 이동
    }

    @GetMapping("/delete")
    public String deletePost(@RequestParam("id") Long postId, HttpSession session) {
        // 세션에서 로그인 정보 가져오기
        Member loginMember = (Member) session.getAttribute("loginMember");

        // 로그인 안 했으면 목록으로 튕겨내기
        if (loginMember == null) {
            return "redirect:/list";
        }

        // 삭제 서비스 호출 (글 ID와 내 ID를 보냄)
        boardService.deletePost(postId, loginMember.getId());

        // 삭제 후 목록으로 이동
        return "redirect:/list";
    }

    @GetMapping("/write-form")
    public String writeView() {
        return "write"; // templates/write.html 파일로 이동
    }
}
