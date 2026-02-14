package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardControl {

    private final BoardService boardService; // 서비스 주입

    @GetMapping("/list")
    public String list(Model model) {
        // boardService가 글을 다 긁어옴
        List<Board> boards = boardService.findAllPosts();

        // boards라는 이름으로 데이터를 HTML에 전달
        model.addAttribute("postList", boards);

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

    @GetMapping("/write-form")
    public String writeView() {
        return "write"; // templates/write.html 파일로 이동
    }
}
