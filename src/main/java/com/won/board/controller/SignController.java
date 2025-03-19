package com.won.board.controller;

import com.won.board.dto.resquest.UserRequest;
import com.won.board.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sign")
public class SignController {

    @Autowired
    private UserService userService;

    // 회원가입
    @GetMapping("/up")
    public String signUp() {
        return "sign/up";
    }

    @PostMapping("/up")
    public String signUpUser(UserRequest userRequest) {
        userService.registerUser(userRequest);
        return "redirect:/sign/in"; // 회원가입 후 로그인 페이지로 리다이렉트
    }

    // 로그인
    @GetMapping("/in")
    public String signIn() {
        return "sign/in";
    }

    @PostMapping("/in")  // 로그인 처리 부분 수정
    public String signInUser(UserRequest userRequest, HttpSession session) {
        // 사용자 인증 (아이디와 비밀번호로 사용자 찾기)
        boolean isAuthenticated = userService.authenticateUser(userRequest, session);

        if (isAuthenticated) {
            return "redirect:/board/list/1";  // 인증 성공 시 게시판으로 리다이렉트
        } else {
            // 인증 실패 시 로그인 페이지로 돌아가며 오류 메시지 전달
            return "redirect:/sign/in?error";  // 로그인 실패 시 오류 표시
        }
    }
}
