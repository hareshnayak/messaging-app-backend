package com.hareshnayak.messaging_app.utils;

import com.hareshnayak.messaging_app.models.User;
import com.hareshnayak.messaging_app.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DatabaseUtils {
    private final UserRepository userRepository;

    public User saveUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public void updateLastLogin(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new RuntimeException("User not found"));
        user.setLastLoginTime(new java.sql.Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
    }
}
