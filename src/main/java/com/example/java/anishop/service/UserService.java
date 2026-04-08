package com.example.java.anishop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.ApiResponse;
import com.example.java.anishop.model.UserDTO;
import com.example.java.anishop.model.respose.LoginRequest;
import com.example.java.anishop.model.respose.RegisterRequest;


@Service
public interface UserService {
    List<UserDTO> searcUser(String keyword);
    UserDTO searchId(Long id);
    ApiResponse<?> register(RegisterRequest request);
    ApiResponse<?> login(LoginRequest login);

    ApiResponse<?> deleteById(Long id);
}
