package com.example.java.anishop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.reponse.UserDTO;
import com.example.java.anishop.model.request.LoginRequest;
import com.example.java.anishop.model.request.RegisterRequest;


@Service
public interface UserService {
    List<UserDTO> searchUser(String keyword);
    UserDTO searchId(Long id);
    ApiResponse<?> register(RegisterRequest request);
    ApiResponse<?> login(LoginRequest login);
    
    // xóa user 
    ApiResponse<?> deleteById(Long id);
 
}
