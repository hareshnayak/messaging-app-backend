package com.hareshnayak.messaging_app.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationException extends BaseApplicationException {

    public AuthenticationException(String message, String errorCode) {
        super(message, errorCode);
    }
}
