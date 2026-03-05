package com.example.demo.Titanic.dto;

import lombok.Data;

@Data
public class TitanicRequestDto {
    private int pclass;
    private String sex;
    private float age;
    private int sibsp;
    private int parch;
    private float fare;
    private String who;
    private String embark_town;
}
