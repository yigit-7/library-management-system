import { ApiError } from "@/lib/api/core/ApiError"
import { ApiErrorResponse } from "@/lib/api/models/ApiErrorResponse"
import { ValidationErrorDetail } from "@/lib/api/models/ValidationErrorDetail"
import { ErrorCodes } from "@/lib/constants/error-codes"

// Interface to match the actual API response structure for Page objects (VIA_DTO mode)
export interface PageResponse<T = unknown> {
  content: T[];
  page: {
    size: number;
    number: number;
    totalElements: number;
    totalPages: number;
  }
}

export type ParsedApiError = {
  message: string
  validationErrors?: Record<string, string>
  code?: string
}

// Default error messages mapping
const ERROR_MESSAGES: Record<string, string> = {
  [ErrorCodes.EMAIL_ALREADY_EXISTS]: "This email address is already in use.",
  [ErrorCodes.INVALID_CREDENTIALS]: "Invalid email or password.",
  [ErrorCodes.USER_ACCOUNT_LOCKED]: "Your account has been locked due to too many failed attempts.",
  [ErrorCodes.UNAUTHORIZED]: "You are not authorized to perform this action.",
  [ErrorCodes.FORBIDDEN]: "You do not have permission to access this resource.",
  [ErrorCodes.TOKEN_EXPIRED]: "Your session has expired. Please login again.",
  [ErrorCodes.USER_NOT_FOUND]: "User not found.",
  [ErrorCodes.INTERNAL_SERVER_ERROR]: "An internal server error occurred. Please try again later.",
  // Add more default messages here as needed
}

export function parseApiError(error: unknown): ParsedApiError {
  // 1. Handle Generated ApiError
  if (error instanceof ApiError) {
    const body = error.body
    
    if (body && typeof body === 'object') {
        // Check if it matches our standard ApiErrorResponse structure
        if ('error' in body) {
            const apiError = body.error as ApiErrorResponse
            const code = apiError.code

            // Handle Validation Errors specifically
            if (code === ErrorCodes.VALIDATION_ERROR && apiError.details) {
                const validationErrors: Record<string, string> = {}
                apiError.details.forEach((detail: ValidationErrorDetail) => {
                    if (detail.field && detail.message) {
                        validationErrors[detail.field] = detail.message
                    }
                })
                return { 
                    message: apiError.message || "Validation failed", 
                    validationErrors,
                    code
                }
            }

            // Check if we have a predefined friendly message for this error code
            if (code && ERROR_MESSAGES[code]) {
                return { message: ERROR_MESSAGES[code], code }
            }
            
            // Fallback to the message from the backend if available
            if (apiError.message) {
                return { message: apiError.message, code }
            }
        }
        
        // Fallback for other body structures
        if ('message' in body) {
             return { message: String(body.message) }
        }
    }

    return { message: error.message || "An API error occurred" }
  }
  
  // 2. Handle Standard JS Errors (Network, etc.)
  if (error instanceof Error) {
    // Handle connection errors (e.g., backend is down)
    if (error.message.includes("fetch failed") || error.message.includes("ECONNREFUSED")) {
        return { message: "Unable to connect to the server. Please check your internet connection or try again later." }
    }
    return { message: error.message }
  }
  
  // 3. Fallback for unknown error types
  return { message: "An unexpected error occurred" }
}
