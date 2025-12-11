package me.seyrek.library_management_system.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.seyrek.library_management_system.exception.ApiError;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private LocalDateTime timestamp;
    private String message;
    private T data;
    private ApiError error;

    // Başarılı: data var
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }

    // Başarılı: data yok
    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .success(true)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // Başarılı: data yok, mesaj var
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .timestamp(LocalDateTime.now())
                .message(message)
                .build();
    }

    // Başarısız: (GlobalExceptionHandler paşa hazretleri kullanacak)
    public static <T> ApiResponse<T> failure(ApiError error) {
        return ApiResponse.<T>builder()
                .success(false)
                .timestamp(LocalDateTime.now())
                .error(error)
                .build();
    }
}