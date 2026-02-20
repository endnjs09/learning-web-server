package com.example.demo.comment;

import com.example.demo.comment.dto.CommentRequest;
import com.example.demo.member.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentAPIControl {

    private final CommentService commentService;

    @PostMapping("/write")
    public ResponseEntity<?> writeComment(@RequestBody CommentRequest req, HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        commentService.writeComment(req.getBoardId(), loginMember.getId(), req.getContent());
        return ResponseEntity.ok("댓글이 등록되었습니다.");
    }
}
