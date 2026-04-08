package com.example.java.anishop.model.respose;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message="Email không đươc rổng")
    @Email(message="Email không hợp lệ")
    private String email;

    @NotBlank(message="password không được trống")
    private String password;
}
