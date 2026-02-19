package com.example.demo.member;

import com.example.demo.config.LoginInterceptor;
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
                .addPathPatterns("/write-form", "/write", "/post/**")
                // [수정] /login-form은 이제 안 쓰니까 삭제하고, 회원가입 폼(/join-form)을 추가합니다.
                .excludePathPatterns("/list", "/login", "/join", "/join-form", "/css/**", "/js/**");
    }
}
