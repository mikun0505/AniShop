package com.example.java.anishop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.request.LoginRequest;
import com.example.java.anishop.model.request.RegisterRequest;
import com.example.java.anishop.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.constraints.Min;




@RestController
public class UserAPI {

    @Autowired
    private UserService userService;
    
    @CacheEvict(value="user",allEntries=true)
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

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<ApiResponse<?>> getUser(@PathVariable @Min(1) Long userId) {
        return ResponseEntity.ok(userService.searchId(userId));
    }
    

    
    @DeleteMapping("/api/users/{userId}")
    @CacheEvict(value="user",allEntries=true)
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable @Min(1) Long userId){
        return ResponseEntity.ok(userService.deleteById(userId));
    }
}
