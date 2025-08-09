package com.hareshnayak.messaging_app.validators;

import com.hareshnayak.messaging_app.exceptions.AuthenticationException;
import com.hareshnayak.messaging_app.models.AuthRequest;

public class ValidationUtil {

    public static void validateAuthRequest(AuthRequest loginRequest) {
         if(!isValidUsername(loginRequest.getUsername()) && isValidPassword(loginRequest.getPassword())){
            throw new AuthenticationException("Invalid username or password", "AUTH001");
         }
    }

    public static boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty() && username.length() >= 3 && username.length() <= 20;
    }

    public static boolean isValidPassword(String password) {
        return password != null && !password.trim().isEmpty() && password.length() >= 6 && password.length() <= 20;
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}
