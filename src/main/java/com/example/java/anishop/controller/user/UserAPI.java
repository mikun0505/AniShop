package com.example.java.anishop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.anishop.model.ApiResponse;
import com.example.java.anishop.model.respose.LoginRequest;
import com.example.java.anishop.model.respose.RegisterRequest;
import com.example.java.anishop.service.UserService;

import jakarta.validation.Valid;



@RestController
public class UserAPI {

    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> postMethodName(
        @Valid @RequestBody RegisterRequest request) {
            return  ResponseEntity.ok(userService.register(request));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid
         @RequestBody LoginRequest request){

        return ResponseEntity.ok(userService.login(request));

    }
}
