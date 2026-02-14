package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 세션 가져오기
        HttpSession session = request.getSession();

        // 세션에 로그인 정보(loginMember) 있는지 확인
        if (session.getAttribute("loginMember") == null) {

            // 로그인 정보 없으면 페이지로 쫓아내고(Redirect) 다음 단계로 못 가게 막음
            response.sendRedirect("/login-form");
            return false;
        }

        // 로그인 정보 있으면 통과
        return true;
    }
}
