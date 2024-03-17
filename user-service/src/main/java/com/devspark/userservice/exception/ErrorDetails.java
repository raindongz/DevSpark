package com.devspark.userservice.exception;

public record ErrorDetails(String timestamp, String message, String path, String errorCode) {
}
