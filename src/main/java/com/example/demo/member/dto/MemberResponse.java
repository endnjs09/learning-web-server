package com.example.demo.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 가입 응답
@Getter
@AllArgsConstructor
public class MemberResponse {
    private String username;
    private String grade;
    private int points;
}