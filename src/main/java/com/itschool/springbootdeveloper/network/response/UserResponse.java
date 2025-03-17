package com.itschool.springbootdeveloper.network.response;

import com.itschool.springbootdeveloper.domain.User;

public class UserResponse {
    private Long id;

    private String email;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }
}
