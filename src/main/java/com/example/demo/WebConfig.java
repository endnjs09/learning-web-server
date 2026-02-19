package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // 설정 파일 (서버 켜지면 이 설계도 부터 읽음)
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer{

    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/write-form", "/write", "/post/**") // 해당 주소들은 검사
                .excludePathPatterns("/login-form", "/login", "/join", "/list"); // 해당 주소들은 그냥 통과
    }
}
