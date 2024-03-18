package com.devspark.authservice.exception;

public record ErrorDetails(String timestamp, String message, String path, String errorCode) {
}
