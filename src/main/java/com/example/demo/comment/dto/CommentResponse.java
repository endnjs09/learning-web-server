package com.example.demo.comment.dto;

import com.example.demo.comment.Comment;
import lombok.Getter;

// 댓글 목록 출력
@Getter
public class CommentResponse {
    private Long id;
    private String content;
    private String writerName;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.writerName = comment.getWriter().getUsername();
    }
}