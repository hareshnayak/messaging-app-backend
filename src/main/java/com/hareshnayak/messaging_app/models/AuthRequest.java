package com.hareshnayak.messaging_app.models;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
