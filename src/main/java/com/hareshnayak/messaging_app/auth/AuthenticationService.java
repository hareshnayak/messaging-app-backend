package com.hareshnayak.messaging_app.auth;

import com.hareshnayak.messaging_app.models.LoginResponse;
import com.hareshnayak.messaging_app.models.User;
import com.hareshnayak.messaging_app.repos.UserRepository;
import com.hareshnayak.messaging_app.utils.DatabaseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final DatabaseUtils databaseUtils;

    public LoginResponse authenticateUser(String username, String password) {
        try {
            return userRepository.findById(username)
                    .filter(user -> (passwordEncoder.matches(password, user.getPassword())))
                    .map(this::processSuccessfulAuthentication)
                    .orElse(createFailedLoginResponse(username, "Invalid credentials"));
        } catch (Exception e) {
            log.error("Authentication error for user {}: {}", username, e.getMessage(), e);
            return createFailedLoginResponse(username, "Authentication failed due to an error");
        }
    }

    private LoginResponse processSuccessfulAuthentication(User user) {
        databaseUtils.updateLastLogin(user);
        String token = jwtService.generateToken(user.getUsername());
        return new LoginResponse(
                token,
                user.getUsername(),
                "Login successful"
        );
    }

    private LoginResponse createFailedLoginResponse(String username, String message) {
        return new LoginResponse(null, username, message);
    }
}
