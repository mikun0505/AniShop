package com.example.java.anishop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.service.OrderDetailService;

import jakarta.validation.constraints.Min;


@RestController
public class OrderDetailAPI {

    @Autowired
    private OrderDetailService orderDetailService;
    // Lấy tất cả những sản phẩm đã được Order theo mã order
    @GetMapping("/api/orderDetails/orderId")
    public ResponseEntity<ApiResponse<?>> getMethodName(@PathVariable @Min(1) Long orderId) {
        return ResponseEntity.ok(orderDetailService.getOrderDetails(orderId));
    }
    
}
