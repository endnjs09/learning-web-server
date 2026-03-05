package com.example.demo.member.dto;
import lombok.Getter;
import lombok.Setter;

// 가입 요청
@Getter @Setter
public class MemberRequest {
    private String name;
    private String password;
}
