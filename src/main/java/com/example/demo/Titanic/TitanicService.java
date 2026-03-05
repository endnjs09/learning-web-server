package com.example.demo.Titanic;

import com.example.demo.Titanic.dto.TitanicRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class TitanicService {
    public Map<String, Object> getSurvivalPrediction(TitanicRequestDto dto) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8000/predict"; // FastAPI 주소

        // FastAPI로부터 받은 JSON 결과를 Map으로 변환해서 리턴
        ResponseEntity<Map> response = restTemplate.postForEntity(url, dto, Map.class);
        return response.getBody();
    }
}
