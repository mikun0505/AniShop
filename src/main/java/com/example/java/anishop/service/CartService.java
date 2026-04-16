package com.example.java.anishop.service;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.repository.entity.Carts;

@Service
public interface CartService {
    // vào giỏ hàng của mình
    ApiResponse<?> myCart(Long cartId);
    // kiểm tra xem có đúng là giỏ hàng của mình kh
    void validateCartOwner(String email,Carts carts);
}
