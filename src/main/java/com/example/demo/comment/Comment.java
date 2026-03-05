package com.example.demo.comment;

import com.example.demo.board.Board;
import com.example.demo.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // 댓글 : 게시글 = N : 1 관계
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    // 댓글 : 작성자 = N : 1 관계
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member writer;
}