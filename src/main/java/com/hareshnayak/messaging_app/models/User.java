package com.hareshnayak.messaging_app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity(name = "users")
public class User {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;
    @Column(name = "password")
    private String password;
    @Column(name = "last_login_time")
    private Timestamp lastLoginTime;
}
