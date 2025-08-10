package com.hareshnayak.auth_service.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BaseApplicationException extends RuntimeException {
    String message;
    String errorCode;

    public BaseApplicationException(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
