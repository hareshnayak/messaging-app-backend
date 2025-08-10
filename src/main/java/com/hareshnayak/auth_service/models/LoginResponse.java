package com.hareshnayak.auth_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String username;
    private String message;
}
