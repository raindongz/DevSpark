package com.devspark.matchservice.exception.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class LikeOrUnlikeException extends RuntimeException{
    private String message;

    public LikeOrUnlikeException(String message) {
        super(message);
    }
}
