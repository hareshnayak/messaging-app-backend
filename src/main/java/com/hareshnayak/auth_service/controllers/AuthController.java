package com.hareshnayak.auth_service.controllers;

import com.hareshnayak.auth_service.auth.AuthenticationService;
import com.hareshnayak.auth_service.auth.JwtService;
import com.hareshnayak.auth_service.models.BaseResponse;
import com.hareshnayak.auth_service.models.AuthRequest;
import com.hareshnayak.auth_service.models.LoginResponse;
import com.hareshnayak.auth_service.models.User;
import com.hareshnayak.auth_service.utils.DatabaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.hareshnayak.auth_service.validators.ValidationUtil.validateAuthRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final DatabaseUtils databaseUtils;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@RequestBody AuthRequest loginRequest) {
        validateAuthRequest(loginRequest);
        User user;
        try {
            user = databaseUtils.saveUser(
                    loginRequest.getUsername(),
                    passwordEncoder.encode(loginRequest.getPassword())
            );
        }catch (Exception e) {
            log.error("Registration error for user {}: {}", loginRequest.getUsername(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseResponse("Registration failed", "false"));
        }

        log.info("User {} registered successfully", loginRequest.getUsername());
        return ResponseEntity.ok(new BaseResponse("User registered successfully", "true", user));
    }


    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody AuthRequest loginRequest) {
        validateAuthRequest(loginRequest);

        LoginResponse loginResponse = authenticationService.authenticateUser(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        );

        if (loginResponse.getToken() != null) {
            log.info("User {} logged in successfully", loginRequest.getUsername());
            return ResponseEntity.ok(
                new BaseResponse("Login successful", "true", loginResponse)
            );
        } else {
            log.info("Invalid login attempt for user {}", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new BaseResponse("Invalid credentials", "false", null));
        }
    }
}
