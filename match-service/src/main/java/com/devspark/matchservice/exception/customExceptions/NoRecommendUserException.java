package com.devspark.matchservice.exception.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoRecommendUserException extends RuntimeException{
    private String message;

    public NoRecommendUserException(String message) {
        super(message);
    }
}
