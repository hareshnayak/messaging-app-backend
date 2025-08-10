package com.hareshnayak.auth_service.utils;

import com.hareshnayak.auth_service.models.User;
import com.hareshnayak.auth_service.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class DatabaseUtils {
    private final UserRepository userRepository;

    public User saveUser(String username, String password) {
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
    }

    @Transactional
    public void updateLastLogin(User user) {
        user.setLastLoginTime(new java.sql.Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
    }
}
