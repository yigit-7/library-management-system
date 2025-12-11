package me.seyrek.library_management_system.exception;

import lombok.Getter;

@Getter
public class BaseApiException extends RuntimeException {
    private final ErrorCode errorCode;

    public BaseApiException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}