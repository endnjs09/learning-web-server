package com.example.demo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity     // 이 클래스는 DB 테이블임을 선언
@Getter @Setter     // 필드에 대한 Getter/Setter 자동 생성
public class Member {

    @Id     // 표에서 각 줄을 구분하는 고유 번호 (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 번호 자동 증가
    private Long id;

    private String username;    // 이름
    private int points;     // 점수
    private String grade;   // 등급
    private String password;    // 비번

    public Member() {}

}
