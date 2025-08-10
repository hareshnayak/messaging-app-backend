package com.hareshnayak.auth_service.controllers;

import com.hareshnayak.auth_service.auth.AuthenticationService;
import com.hareshnayak.auth_service.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.hareshnayak.auth_service.validators.ValidationUtil.validateAuthRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@RequestBody AuthRequest authRequest) {
        validateAuthRequest(authRequest);
        RegisterResponse response = authenticationService.registerUser(authRequest);
        if (response.getUserId() == null) {
            log.error("Registration failed for user {}", authRequest.getUsername());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse(response.getMessage(), "false", null));
        }
        return ResponseEntity.ok(new BaseResponse("User registered successfully", "true", response));
    }


    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody AuthRequest loginRequest) {
        validateAuthRequest(loginRequest);

        LoginResponse loginResponse = authenticationService.authenticateUser(loginRequest);

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
