package com.example.demo.board;

import com.example.demo.comment.Comment;
import com.example.demo.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;   // 제목
    private String content; // 내용

    @ManyToOne  // 여러 개의 글이 한 회원에 속함
    @JoinColumn(name = "member_id") // DB에서 member_id라는 이름의 외래키 칸 생성
    private Member writer;

    // mappedBy = "board" >> Comment 클래스에 있는 board 변수에 의해 관리됨
    // cascade = CascadeType.REMOVE >> 게시글이 삭제되면 달린 댓글도 같이 삭제됨
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public Board() {}
}
