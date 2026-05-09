package com.example.java.anishop.model.reponse;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor  // sinh ra: public ApiResponse() {}
@AllArgsConstructor // sinh ra: public ApiResponse(int status, String message, T data) {}
public class ApiResponse<T> implements Serializable{
    private static final Long CacheProductId=1L;
    private int status;
    private String message;
    private T data;
}