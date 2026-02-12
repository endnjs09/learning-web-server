package com.example.demo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    public Board() {}
}
