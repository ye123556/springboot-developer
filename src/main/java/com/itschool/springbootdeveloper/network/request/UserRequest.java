package com.itschool.springbootdeveloper.network.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String email;

    private String password;
}
