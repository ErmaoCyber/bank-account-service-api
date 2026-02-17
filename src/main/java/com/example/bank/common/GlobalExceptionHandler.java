package com.example.bank.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1) Bean Validation（@Valid）参数校验失败：400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest req
    ) {
        Map<String, Object> fieldErrors = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fe.getField(), fe.getDefaultMessage());
        }

        ApiError body = new ApiError(
                400,
                "Bad Request",
                "Validation failed",
                req.getRequestURI(),
                Map.of("fields", fieldErrors)
        );

        return ResponseEntity.badRequest().body(body);
    }

    // 2) Path/Query 参数校验失败（可选）：400
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest req
    ) {
        ApiError body = new ApiError(
                400,
                "Bad Request",
                "Constraint violation",
                req.getRequestURI(),
                Map.of("violations", ex.getMessage())
        );
        return ResponseEntity.badRequest().body(body);
    }

    // 3a) 404 Not Found
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
        ApiError body = new ApiError(
                404,
                "Not Found",
                ex.getMessage(),
                req.getRequestURI(),
                Map.of()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // 3b) 409 Conflict（余额不足/重复开户等）
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ConflictException ex, HttpServletRequest req) {
        ApiError body = new ApiError(
                409,
                "Conflict",
                ex.getMessage(),
                req.getRequestURI(),
                Map.of()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // 3c) 400 Bad Request（参数逻辑错误）
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
        ApiError body = new ApiError(
                400,
                "Bad Request",
                ex.getMessage(),
                req.getRequestURI(),
                Map.of()
        );
        return ResponseEntity.badRequest().body(body);
    }


    // 4) 兜底：500（真未知异常）
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAny(
            Exception ex,
            HttpServletRequest req
    ) {
        ApiError body = new ApiError(
                500,
                "Internal Server Error",
                "Unexpected error",
                req.getRequestURI(),
                Map.of("exception", ex.getClass().getSimpleName())
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
