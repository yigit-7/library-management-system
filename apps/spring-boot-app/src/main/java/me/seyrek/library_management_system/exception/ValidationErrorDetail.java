package me.seyrek.library_management_system.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ValidationErrorDetail(
        String field,
        String message,
        Object rejectedValue
) {}