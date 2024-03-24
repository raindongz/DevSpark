package com.devspark.authservice.exception.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class JWTSignException extends RuntimeException {
    private String message;

    public JWTSignException(String message) {
        super(message);
    }
}
