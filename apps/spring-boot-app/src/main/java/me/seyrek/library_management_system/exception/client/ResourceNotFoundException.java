package me.seyrek.library_management_system.exception.client;

import me.seyrek.library_management_system.exception.BaseApiException;
import me.seyrek.library_management_system.exception.ErrorCode;

public class ResourceNotFoundException extends BaseApiException {
    public ResourceNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
