package com.example.demo.config;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice
//@RestControllerAdvice // 응답이 HTML이 아니라 JSON으로
@ControllerAdvice // @RestControllerAdvice 대신 이걸 쓰고 메서드마다 결정
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {

        // 요청 주소에 /api가 포함되어 있다면 JSON(ResponseEntity) 반환
        if (request.getRequestURI().startsWith("/api")) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // 그 외 일반적인 페이지 요청이라면 기존처럼 HTML 페이지 반환
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", e.getMessage());
        mav.setViewName("error/400");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public Object handleAll(Exception e, HttpServletRequest request) {
        if (request.getRequestURI().startsWith("/api")) {
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다.");
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", "알 수 없는 에러가 발생했습니다.");
        mav.setViewName("error/500");
        return mav;
    }


    // IllegalArgumentException(잘못된 인자 에러) 터지면 이 메서드가 실행 됨
//    @ExceptionHandler(IllegalArgumentException.class)
//    public String handleIllegalArgument(IllegalArgumentException e, Model model) {
//
//        // 에러 메세지를 담아서 에러 전용 HTML 페이지로 보냄
//        model.addAttribute("errorMessage", e.getMessage());
//        return "error/400";
//    }
//
//    // 그 외 예상치 못한 모든 에러 처리
//    @ExceptionHandler(Exception.class)
//    public String handleAllException(Exception e, Model model) {
//        model.addAttribute("errorMessage", "알 수 없는 문제가 발생했습니다.");
//        return "error/500";
//    }
}
