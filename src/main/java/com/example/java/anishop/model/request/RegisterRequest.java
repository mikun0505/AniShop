package com.example.java.anishop.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message="Email không được rổng")
    @Email(message="Email không hợp lệ")
    private String email;

    @NotBlank(message="Password không được trống")
    @Size(min=6, message="Password tối thiểu 6  kí tự")
    private String password;

    @NotBlank(message="Tên không được trống")
    private String name;
}
