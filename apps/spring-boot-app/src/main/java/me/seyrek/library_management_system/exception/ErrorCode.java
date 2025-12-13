package me.seyrek.library_management_system.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // =====================================================================
    // 1. GENERIC SYSTEM ERRORS (E5xxx)
    // =====================================================================
    INTERNAL_SERVER_ERROR("E5000", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_IMPLEMENTED("E5001", "Feature not implemented", HttpStatus.NOT_IMPLEMENTED),

    // =====================================================================
    // 2. AUTHENTICATION & SECURITY (E2xxx)
    // =====================================================================
    UNAUTHORIZED("E2001", "Authentication failed. Please provide a valid token.", HttpStatus.UNAUTHORIZED),
    INVALID_CREDENTIALS("E2002", "Invalid email or password.", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED("E2004", "Access token has expired.", HttpStatus.UNAUTHORIZED),

    FORBIDDEN("E2003", "You do not have permission to access this resource.", HttpStatus.FORBIDDEN),
    REFRESH_TOKEN_EXPIRED("E2005", "Refresh token expired. Please login again.", HttpStatus.FORBIDDEN), // Changed from 400 to 403
    USER_ACCOUNT_LOCKED("E2006", "User account is locked.", HttpStatus.FORBIDDEN),

    // =====================================================================
    // 3. DATA & VALIDATION (E3xxx)
    // =====================================================================
    // 400: Bad Request
    VALIDATION_ERROR("E3001", "Validation failed.", HttpStatus.BAD_REQUEST),
    MALFORMED_REQUEST("E3002", "The request body is malformed or missing.", HttpStatus.BAD_REQUEST),
    INVALID_ARGUMENT_FORMAT("E3003", "An argument has an invalid format.", HttpStatus.BAD_REQUEST),

    ENDPOINT_NOT_FOUND("E3004", "The requested endpoint does not exist.", HttpStatus.NOT_FOUND),
    RESOURCE_NOT_FOUND("E3005", "Requested resource was not found.", HttpStatus.NOT_FOUND),
    METHOD_NOT_SUPPORTED("E3006", "HTTP method is not supported for this endpoint.", HttpStatus.METHOD_NOT_ALLOWED),
    UNSUPPORTED_MEDIA_TYPE("E3007", "The specified media type is not supported.", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    DATA_INTEGRITY_VIOLATION("E3008", "Data integrity violation.", HttpStatus.CONFLICT),

    // =====================================================================
    // 4. BUSINESS LOGIC - USER & GENERIC (E1xxx)
    // =====================================================================
    USER_NOT_FOUND("E1001", "User not found", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS("E1002", "Email already exists", HttpStatus.CONFLICT), // Fixed ID conflict
    REFRESH_TOKEN_NOT_FOUND("E1003", "Refresh token not found", HttpStatus.NOT_FOUND),

    // =====================================================================
    // 5. BUSINESS LOGIC - LIBRARY DOMAIN (E4xxx)
    // =====================================================================
    AUTHOR_NOT_FOUND("E4001", "Author not found", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND("E4002", "Category not found", HttpStatus.NOT_FOUND),
    BOOK_NOT_FOUND("E4003", "Book not found", HttpStatus.NOT_FOUND),
    BOOK_ALREADY_EXISTS("E4004", "Book with this ISBN already exists", HttpStatus.CONFLICT);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}