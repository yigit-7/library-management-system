package me.seyrek.library_management_system.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        String code,
        String message,
        List<ValidationErrorDetail> details
) {
    public ApiError(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public ApiError(ErrorCode errorCode, String customMessage) {
        this(errorCode.getCode(), customMessage, null);
    }

    public ApiError(List<ValidationErrorDetail> details) {
        this(ErrorCode.VALIDATION_ERROR.getCode(), ErrorCode.VALIDATION_ERROR.getMessage(), details);
    }
}
