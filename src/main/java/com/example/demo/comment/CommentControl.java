package com.example.demo.comment;

import com.example.demo.member.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentControl {
    private final CommentService commentService;

    @PostMapping("/comment/write")
    public String writeComment(@RequestParam("boardId") Long boardId,
                               @RequestParam("content") String content,
                               HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember != null) {
            commentService.writeComment(boardId, loginMember.getId(), content);
        }

        // 댓글 작성 후 보던 글로 다시 리다이렉트
        return "redirect:/view?id=" + boardId;
    }
}