package com.example.java.anishop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.request.CaregoryRequest;
import com.example.java.anishop.service.CaregoryService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;




@RestController
public class CaregoryAPI {
    @Autowired
    private CaregoryService caregoryService;
    @GetMapping("/api/caregories/{id}")
    public ResponseEntity<ApiResponse<?>> getMethodName(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(caregoryService.findCaregoryId(id));
    }

    @GetMapping("/api.caregories")
    public ResponseEntity<ApiResponse<?>> getMethodName() {
        return ResponseEntity.ok(caregoryService.findAllCaregory());
    }
    
    @PostMapping("/api/caregories")
    public ResponseEntity<ApiResponse<?>> postMethodName(@Valid @RequestBody CaregoryRequest request) {
        //TODO: process POST request

        
        return ResponseEntity.ok(caregoryService.createdCaregori(request));
    }

    @PutMapping("/api/caregories")
    public ResponseEntity<ApiResponse<?>> putMethodName(@Valid @RequestBody CaregoryRequest request) {
        //TODO: process POST request

        
        return ResponseEntity.ok(caregoryService.createdCaregori(request));
    }

    @DeleteMapping("/api/caregories/{id}")
    public ResponseEntity<ApiResponse<?>> deleteById(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(caregoryService.deletedCaregory(id));
    }
}
