package com.hareshnayak.messaging_app.controllers;

import com.hareshnayak.messaging_app.models.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<BaseResponse> test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(
            new BaseResponse("Authenticated successfully", "true", "Hello " + username + "!")
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<BaseResponse> profile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(
            new BaseResponse("Profile data retrieved", "true",
                "Profile for user: " + username)
        );
    }
}
