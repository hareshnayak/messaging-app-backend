package com.hareshnayak.messaging_app.auth;

import com.hareshnayak.messaging_app.models.LoginResponse;
import com.hareshnayak.messaging_app.repos.UserRepository;
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

   public LoginResponse authenticateUser(String username, String password) {
       try {
           return userRepository.findById(username)
                   .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                   .map(user -> new LoginResponse(
                           jwtService.generateToken(username),
                           username,
                           "Login successful"
                   ))
                   .orElseGet(() -> new LoginResponse(
                           null,
                           username,
                           "Invalid credentials"
                   ));
       } catch (Exception e) {
           log.error("Authentication error for user {}: {}", username, e.getMessage());
           return new LoginResponse(
                   null,
                   username,
                   "Authentication failed due to an error"
           );
       }
   }
}
