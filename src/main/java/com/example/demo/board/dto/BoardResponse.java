package com.example.demo.board.dto;

import com.example.demo.board.Board;
import com.example.demo.comment.dto.CommentResponse;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResponse {
    private Long id;
    private String title;
    private String content;
    private String writerName; // Member 객체 전체 대신 이름만 담음
    private List<CommentResponse> comments;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writerName = board.getWriter().getUsername();

        if (board.getComments() != null) {
            this.comments = board.getComments().stream()
                    .map(CommentResponse::new)
                    .collect(Collectors.toList());
        }
    }
}