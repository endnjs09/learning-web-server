package com.example.demo.comment.dto;

import lombok.Getter;
import lombok.Setter;

// 댓글 등록
@Getter @Setter
public class CommentRequest {
    private Long boardId;
    private String content;
}