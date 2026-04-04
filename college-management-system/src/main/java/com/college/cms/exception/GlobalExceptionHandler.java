package com.college.cms.exception;

import com.college.cms.common.base.constant.ErrorCodes;
import com.college.cms.common.base.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomException(
            CustomException ex,
            HttpServletRequest request
    ) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .status(ex.getStatus().value())
                .message(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed")
                .errorCode(ErrorCodes.VALIDATION_ERROR)
                .fieldErrors(fieldErrors)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request
    ) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .status(HttpStatus.FORBIDDEN.value())
                .message("Access denied. You don't have permission to access this resource")
                .errorCode(ErrorCodes.ACCESS_DENIED)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request
    ) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Invalid username or password")
                .errorCode(ErrorCodes.BAD_CREDENTIALS)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateEntry(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {
        String message = "Duplicate entry found";
        Throwable root = ex.getRootCause();
        if (root != null && root.getMessage() != null) {
            String rootMsg = root.getMessage();
            if (rootMsg.contains("Duplicate entry")) {
                message = "Duplicate value already exists";
            }
        }

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .status(HttpStatus.CONFLICT.value())
                .message(message)
                .errorCode(ErrorCodes.DUPLICATE_FOUND)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request
    ) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message("HTTP method '" + ex.getMethod() + "' is not supported for this endpoint")
                .errorCode(ErrorCodes.METHOD_NOT_ALLOWED)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingParams(
            MissingServletRequestParameterException ex,
            HttpServletRequest request
    ) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Required parameter '" + ex.getParameterName() + "' is missing")
                .errorCode(ErrorCodes.VALIDATION_ERROR)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(
            Exception ex,
            HttpServletRequest request
    ) {
        ex.printStackTrace();

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Something went wrong. Please try again later")
                .errorCode(ErrorCodes.INTERNAL_ERROR)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}