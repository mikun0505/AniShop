package com.example.java.anishop.service;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.reponse.ApiResponse;

@Service
public interface CartItemService {
    // tìm danh sách sản phẩm trong giỏ hàng 
    ApiResponse<?> getCartItem(Long id);
    // thêm sản phẩm vào giỏ hàng
    ApiResponse<?> addCartItem(Long userId,Long productId,Long quantity);
    //update CartItem
    ApiResponse<?> updateCartItem(Long cartItemId,Long quantity);
    // xóa sản phẩm trong giỏ hàng theo Id
    ApiResponse<?> deleteCartItemId(Long cartItemId); 
    // xóa tất cả sản phẩm trong giỏ hàng 
    ApiResponse<?> deleteAllCarItem(Long urserId);
}
