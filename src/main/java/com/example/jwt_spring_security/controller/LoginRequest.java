package com.example.jwt_spring_security.controller;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;

}
