package com.devspark.chatservice.exception.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SaveRecordException extends RuntimeException{
    private String message;

    public SaveRecordException(String message) {
        super(message);
    }
}
