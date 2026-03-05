package com.example.demo.Titanic;

import com.example.demo.Titanic.dto.TitanicRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class TitanicService {
    public Map<String, Object> getSurvivalPrediction(TitanicRequestDto dto) {
        RestTemplate restTemplate = new RestTemplate(); // HTTP 통신(동기)
        // 자바가 밑의 url로 요청을 보내면 FastAPI 모델을 돌려 결과를 JSON 형태로 돌려줌
        String url = "http://localhost:8000/predict"; // FastAPI 주소

        // FastAPI로부터 받은 JSON 결과를 Map으로 변환해서 리턴
        ResponseEntity<Map> response = restTemplate.postForEntity(url, dto, Map.class);
        // url: 목적지 주소
        // dto: 전송할 데이터 (사용자가 입력한 값) JSON 형식으로 보냄
        // Map.class: JSON 데이터를 Map 형태로 받음

        return response.getBody();
        // { "survived" = 1, "probability" = 0.85 }
    }
}
