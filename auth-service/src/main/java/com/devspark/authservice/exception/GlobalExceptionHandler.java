package com.devspark.authservice.exception;

import com.devspark.authservice.exception.customExceptions.IncorrectPasswordException;
import com.devspark.authservice.exception.customExceptions.JWTSignException;
import com.devspark.authservice.exception.customExceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ErrorDetails> handleGlobalException(RuntimeException exception,
//                                                              WebRequest webRequest){
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
//                exception.getMessage(),
//                webRequest.getDescription(false),
//                "INTERNAL SERVER ERROR"
//        );
//
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(UserNotFoundException exception,
                                                              WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                exception.getMessage(),
                webRequest.getDescription(false),
                "USER NOT EXIST"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(IncorrectPasswordException exception,
                                                              WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                exception.getMessage(),
                webRequest.getDescription(false),
                "incorrect password"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTSignException.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(JWTSignException exception,
                                                              WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                exception.getMessage(),
                webRequest.getDescription(false),
                "jwt encrypt fail"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
