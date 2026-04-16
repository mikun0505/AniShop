package com.example.java.anishop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.request.OrderRequest;
import com.example.java.anishop.service.OrderService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;




@RestController
public class OrderAPI {
    @Autowired
    private OrderService orderService;

    //Lấy list ngdung đặt hàng
    @GetMapping("/api/orders/{userId}")
    public ResponseEntity<ApiResponse<?>> getOrderByUserId(@PathVariable @Min(1) Long userId) {
        return ResponseEntity.ok(orderService.getOrderByUserId(userId));
    }
    
    //Tìm theo mã đơn hàng
    @GetMapping("/api/orders/{orderId}")
    public ResponseEntity<ApiResponse<?>> getOrderId(@PathVariable @Min(1) Long orderId) {
        return ResponseEntity.ok(orderService.getOrderId(orderId));
    }
    
    //update trạng thái
    @PutMapping("/api/orders/{orderId}")
    public ResponseEntity<ApiResponse<?>> updateStatus(@Valid @RequestBody OrderRequest request) {
        //TODO: process PUT request
        
        return ResponseEntity.ok(orderService.updateOrderStatus(request));
    }

    // Hủy đơn hàng
    @DeleteMapping("/api/orders/{orderId}")
    public ResponseEntity<ApiResponse<?>> deletedOrders(@PathVariable @Min(1) Long orderId){
        return ResponseEntity.ok(orderService.deletedOrder(orderId));
    }
}
