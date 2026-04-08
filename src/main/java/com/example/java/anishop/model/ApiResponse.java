package com.example.java.anishop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor  // sinh ra: public ApiResponse() {}
@AllArgsConstructor // sinh ra: public ApiResponse(int status, String message, T data) {}
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
}