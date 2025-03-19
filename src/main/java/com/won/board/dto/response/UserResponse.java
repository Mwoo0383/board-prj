package com.won.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String userId;
    private String password;
    private String email;
    private String username;
    private String phone;
}
