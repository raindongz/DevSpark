package com.devspark.matchservice.exception;

public record ErrorDetails(String timestamp, String message, String path, String errorCode) {
}
