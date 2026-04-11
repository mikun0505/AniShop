package com.example.java.anishop.exception;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.java.anishop.model.reponse.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<?>> handlEntity(AppException e){
        return ResponseEntity.status(e.getStatusCode()).body(ApiResponse.builder().status(e.getStatusCode())
        .message(e.getMessage())
        .data(null)
        .build());
    }

           // ✅ Handle lỗi @Valid — thiếu cái này bị trừ điểm ngay
           @ExceptionHandler(MethodArgumentNotValidException.class)
           public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException e) {
               String message = e.getBindingResult().getFieldErrors()
                   .stream()
                   .map(err -> err.getField() + ": " + err.getDefaultMessage())
                   .collect(Collectors.joining(", "));
       
               return ResponseEntity.status(400).body(
                   ApiResponse.builder()
                       .status(400)
                       .message(message)
                       .data(null)
                       .build()
               );
           }
       
           // ✅ Handle lỗi không xác định — safety net
           @ExceptionHandler(Exception.class)
           public ResponseEntity<ApiResponse<?>> handleGeneral(Exception e) {
               return ResponseEntity.status(500).body(
                   ApiResponse.builder()
                       .status(500)
                       .message("Internal server error")
                       .data(null)
                       .build()
               );
           }
}