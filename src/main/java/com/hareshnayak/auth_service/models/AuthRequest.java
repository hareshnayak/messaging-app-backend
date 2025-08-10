package com.hareshnayak.auth_service.models;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
