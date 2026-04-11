package com.example.java.anishop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.service.UserService;

import jakarta.validation.constraints.Min;

@RestController
public class AdminApi {

    @Autowired
    private UserService userService;
    @DeleteMapping("/api/admin/users/{id}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(userService.deleteById(id));
    }
}
