package com.devspark.chatservice.exception;

public record ErrorDetails(String timestamp, String message, String path, String errorCode) {
}
