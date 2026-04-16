package com.example.java.anishop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.request.CartItemRequest;
import com.example.java.anishop.service.CartItemService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;




@RestController
public class CartItemAPI {

    @Autowired
    private CartItemService cartItemService;
    @GetMapping("/api/cartItems/{id}")
    public ResponseEntity<ApiResponse<?>> getCartItem(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(cartItemService.getCartItem(id));
    }

    @PostMapping("/api/cartItems")
    public ResponseEntity<ApiResponse<?>> postCartItem(@Valid @RequestBody CartItemRequest requets) {
        //TODO: process POST request
        
        return ResponseEntity.ok(cartItemService.addCartItem(requets));
    }
    // xóa 1 sản phẩm trong giỏ hàng

    @DeleteMapping("/api/cartItems/{cartItemid}")
    public ResponseEntity<ApiResponse<?>> deletedCartItem(@PathVariable @Min(1) Long cartItemId){
        return ResponseEntity.ok(cartItemService.deleteCartItemId(cartItemId));
    }

    // xóa tất cả sản phẩm trong giỏ hàng
    @GetMapping("/api/cartItems/All/{userId}")
    public ResponseEntity<ApiResponse<?>> deletedAllCarrtItem(@PathVariable @Min(1) Long userId) {
        return ResponseEntity.ok(cartItemService.deleteAllCarItem(userId));
    }
    
    
}
