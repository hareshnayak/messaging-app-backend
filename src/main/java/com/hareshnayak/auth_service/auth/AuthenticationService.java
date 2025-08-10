package com.hareshnayak.auth_service.auth;

import com.hareshnayak.auth_service.models.*;
import com.hareshnayak.auth_service.repos.UserRepository;
import com.hareshnayak.auth_service.utils.DatabaseUtils;
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

    public LoginResponse authenticateUser(AuthRequest loginRequest) {
        try {
            return userRepository.findById(loginRequest.getUsername())
                    .filter(user -> (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())))
                    .map(this::processSuccessfulAuthentication)
                    .orElse(createFailedLoginResponse(loginRequest.getUsername(), "Invalid credentials"));
        } catch (Exception e) {
            log.error("Authentication error for user {}: {}", loginRequest.getUsername(), e.getMessage(), e);
            return createFailedLoginResponse(loginRequest.getUsername(), "Authentication failed due to an error");
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

    public RegisterResponse registerUser(AuthRequest authRequest) {
        User user = userRepository.findById(authRequest.getUsername()).orElse(null);
        if (user != null) {
            log.error("User {} already exists", authRequest.getUsername());
            return new RegisterResponse(null, authRequest.getUsername(), "User already exists");
        }
        try {
            user = databaseUtils.saveUser(
                    authRequest.getUsername(),
                    passwordEncoder.encode(authRequest.getPassword())
            );
            return new RegisterResponse(
                   user.getUserId(), user.getUsername(),"User registered successfully"
            );
        }catch (Exception e) {
            log.error("Registration error for user {}: {}", authRequest.getUsername(), e.getMessage());
            return new RegisterResponse(null, authRequest.getUsername(),
                    "Registration failed due to an error.");
        }
    }
}
