package com.example.java.anishop.service;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.repository.entity.Shops;

@Service
public interface CheckoutService {
    //
    ApiResponse<?> checkout(Long userId,String address);
    // kiểm tra nếu đó là shop của chính m thì kh thể mua hàng
    void validateShopOwnerUser(Shops shop,String email);
}
