package com.won.board.service;

import com.won.board.dto.resquest.UserRequest;
import com.won.board.entity.User;
import com.won.board.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerUser(UserRequest userRequest) {
        // 중복 검사
        if (userRepository.existsByUserId(userRequest.getUserId())) {
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

        // User 객체 생성 후 저장
        User user = User.builder()
                .userId(userRequest.getUserId())
                .password(encodedPassword)
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .phone(userRequest.getPhone())
                .build();

        userRepository.save(user);
    }

    public boolean authenticateUser(UserRequest userRequest, HttpSession session) {
        // 사용자 아이디로 User 객체 조회
        User user = userRepository.findByUserId(userRequest.getUserId());

        if (user != null && passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            // 비밀번호가 일치하면 세션에 사용자 정보 저장
            session.setAttribute("user", user);
            return true;
        }
        return false; // 인증 실패
    }
}
