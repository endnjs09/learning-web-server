package com.example.demo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // IllegalArgumentException(잘못된 인자 에러) 터지면 이 메서드가 실행 됨
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException e, Model model) {

        // 에러 메세지를 담아서 에러 전용 HTML 페이지로 보냄
        model.addAttribute("errorMessage", e.getMessage());
        return "error/400";
    }

    // 그 외 예상치 못한 모든 에러 처리
    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception e, Model model) {
        model.addAttribute("errorMessage", "알 수 없는 문제가 발생했습니다.");
        return "error/500";
    }
}
