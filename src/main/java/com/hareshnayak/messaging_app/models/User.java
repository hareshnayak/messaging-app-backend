package com.hareshnayak.messaging_app.models;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class User {
    private String username;
    private String password;
    private Timestamp lastLoginTime;
}
