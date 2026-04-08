package com.example.java.anishop.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.java.anishop.model.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<?>> handlEntity(AppException e){
        return ResponseEntity.status(e.getStatusCode()).body(ApiResponse.builder().status(e.getStatusCode())
        .message(e.getMessage())
        .data(null)
        .build());
    }
}
