package com.example.demo.Titanic;

import com.example.demo.Titanic.dto.TitanicRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class TitanicControl {
    private final TitanicService titanicService;

    // 입력 페이지 이동
    @GetMapping("/titanic/form")
    public String showForm() {
        return "titanic/form";
    }

    // 예측 실행 및 결과 보기
    @PostMapping("/titanic/predict")
    public String doPredict(TitanicRequestDto dto, Model model) {
        // AI 서버 호출
        Map<String, Object> result = titanicService.getSurvivalPrediction(dto);

        // 결과값 세팅 (FastAPI가 준 survived, probability)
        model.addAttribute("prediction", result.get("survived").equals(1) ? "생존" : "사망");
        model.addAttribute("probability", result.get("probability"));

        return "titanic/result";
    }
}