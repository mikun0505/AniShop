package com.example.java.anishop.service;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.request.OrderRequest;

@Service
public interface OrderService {
    // lấy danh sách ngdung đặt hàng theo id ngdung
    ApiResponse<?> getOrderByUserId(Long userId);
    //lấy danh sách theo mã đơn hàng
    ApiResponse<?> getOrderId(Long orderId);
    //update trạng thái đơn hàng chỉ ng quản lí và admin dc quyền
    ApiResponse<?> updateOrderStatus(OrderRequest request);
    //hủy đơn hàng
    ApiResponse<?> deletedOrder(Long orderId);

}
