package me.seyrek.library_management_system.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import me.seyrek.library_management_system.common.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Yardımı metod
    private ResponseEntity<ApiResponse<Void>> buildErrorResponse(ErrorCode errorCode, ApiError apiError) {
        return new ResponseEntity<>(ApiResponse.failure(apiError), errorCode.getHttpStatus());
    }

    @ExceptionHandler(BaseApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseApiException(BaseApiException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ApiError apiError = new ApiError(errorCode, ex.getMessage());
        return buildErrorResponse(errorCode, apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(AuthenticationException ex) {
        log.warn("Authentication failed: {}", ex.getMessage());
        ErrorCode errorCode = (ex instanceof BadCredentialsException)
                ? ErrorCode.INVALID_CREDENTIALS
                : ErrorCode.UNAUTHORIZED;

        ApiError apiError = new ApiError(errorCode);
        return buildErrorResponse(errorCode, apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        String detail = String.format("You do not have permission to access this resource: %s", request.getRequestURI());
        log.warn("Access denied: {}", request.getRequestURI());
        ErrorCode errorCode = ErrorCode.FORBIDDEN;
        ApiError apiError = new ApiError(errorCode, detail);
        return buildErrorResponse(errorCode, apiError);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleExpiredJwtException(ExpiredJwtException ex) {
        log.warn("JWT expired: {}", ex.getMessage());
        ErrorCode errorCode = ErrorCode.TOKEN_EXPIRED;
        ApiError apiError = new ApiError(errorCode, "Token has expired.");
        return buildErrorResponse(errorCode, apiError);
    }

    @ExceptionHandler({
            MalformedJwtException.class,
            SignatureException.class,
            UnsupportedJwtException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleInvalidJwtException(Exception ex) {
        log.warn("Invalid JWT: {}", ex.getMessage());
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        ApiError apiError = new ApiError(errorCode, "Invalid token.");
        return buildErrorResponse(errorCode, apiError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Illegal argument: {}", ex.getMessage());
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        ApiError apiError = new ApiError(errorCode, ex.getMessage());
        return buildErrorResponse(errorCode, apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ValidationErrorDetail> details = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    FieldError fieldError = (FieldError) error;
                    return new ValidationErrorDetail(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue());
                })
                .collect(Collectors.toList());

        log.warn("Validation error: {}", details);

        ApiError apiError = new ApiError(details);
        return buildErrorResponse(ErrorCode.VALIDATION_ERROR, apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Malformed JSON request: {}", ex.getMessage());
        ErrorCode errorCode = ErrorCode.MALFORMED_REQUEST;
        ApiError apiError = new ApiError(errorCode);
        return buildErrorResponse(errorCode, apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> details = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());

        log.warn("Constraint validation error: {}", details);

        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        ApiError apiError = new ApiError(errorCode, String.join(", ", details));
        return buildErrorResponse(errorCode, apiError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String detail = String.format("Parameter '%s' should be of type '%s'",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");

        log.warn("Type mismatch: {}", detail);

        ErrorCode errorCode = ErrorCode.INVALID_ARGUMENT_FORMAT;
        ApiError apiError = new ApiError(errorCode, detail);
        return buildErrorResponse(errorCode, apiError);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        String detail = String.format("Missing required parameter: '%s'", ex.getParameterName());
        log.warn("Missing parameter: {}", detail);

        ErrorCode errorCode = ErrorCode.MALFORMED_REQUEST;
        ApiError apiError = new ApiError(errorCode, detail);
        return buildErrorResponse(errorCode, apiError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String detail = String.format("Method '%s' is not supported. Supported methods: %s",
                ex.getMethod(), ex.getSupportedHttpMethods());
        log.warn("Method not allowed: {}", ex.getMethod());

        ErrorCode errorCode = ErrorCode.METHOD_NOT_SUPPORTED;
        ApiError apiError = new ApiError(errorCode, detail);
        return buildErrorResponse(errorCode, apiError);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        String detail = String.format("Media type '%s' is not supported. Supported types: %s",
                ex.getContentType(), ex.getSupportedMediaTypes());
        log.warn("Unsupported media type: {}", ex.getContentType());

        ErrorCode errorCode = ErrorCode.UNSUPPORTED_MEDIA_TYPE;
        ApiError apiError = new ApiError(errorCode, detail);
        return buildErrorResponse(errorCode, apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.warn("Database integrity violation: {}", ex.getMessage());
        ErrorCode errorCode = ErrorCode.DATA_INTEGRITY_VIOLATION;
        ApiError apiError = new ApiError(errorCode);
        return buildErrorResponse(errorCode, apiError);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFound(NoResourceFoundException ex) {
        String detail = String.format("Resource not found: '%s'", ex.getResourcePath());
        log.warn("Resource not found: {}", ex.getMessage());
        ErrorCode errorCode = ErrorCode.ENDPOINT_NOT_FOUND;
        ApiError apiError = new ApiError(errorCode, detail);
        return buildErrorResponse(errorCode, apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex) {
        log.error("An unexpected error occurred", ex);
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        ApiError apiError = new ApiError(errorCode);
        return buildErrorResponse(errorCode, apiError);
    }
}
