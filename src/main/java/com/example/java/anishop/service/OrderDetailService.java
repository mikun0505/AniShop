package com.example.java.anishop.service;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.reponse.ApiResponse;

@Service
public interface OrderDetailService {
    // Lấy chi tiết của 1 đơn hàng
    ApiResponse<?> getOrderDetails(Long orderId);
}
