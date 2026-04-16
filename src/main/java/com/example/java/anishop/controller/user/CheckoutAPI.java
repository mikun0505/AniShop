package com.example.java.anishop.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.service.CheckoutService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;


@RestController
public class CheckoutAPI {
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/api/checkout")
    public ResponseEntity<ApiResponse<?>> checkout(
        @PathVariable @Min(1) Long userId,
        @Valid @RequestBody Map<String,String> body
    ) {
        //TODO: process POST request
        
        return ResponseEntity.ok(checkoutService.checkout(userId, body.get("address")));
    }
    
}
