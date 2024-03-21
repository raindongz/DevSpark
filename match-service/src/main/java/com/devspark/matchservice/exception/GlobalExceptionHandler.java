package com.devspark.matchservice.exception;

import com.devspark.matchservice.exception.customExceptions.LikeOrUnlikeException;
import com.devspark.matchservice.exception.customExceptions.NoRecommendUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoRecommendUserException.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(NoRecommendUserException exception,
                                                              WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                exception.getMessage(),
                webRequest.getDescription(false),
                "INTERNAL SERVER ERROR"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LikeOrUnlikeException.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(LikeOrUnlikeException exception,
                                                              WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                exception.getMessage(),
                webRequest.getDescription(false),
                "INTERNAL SERVER ERROR"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
