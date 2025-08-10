package com.hareshnayak.auth_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private String userId;
    private String username;
    private String message;
}
