package com.example.java.anishop.service.impl;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.java.anishop.converter.MapperConverter;
import com.example.java.anishop.exception.AppException;
import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.reponse.CartDTO;
import com.example.java.anishop.repository.CartRepository;
import com.example.java.anishop.repository.UserRepository;
import com.example.java.anishop.repository.entity.Carts;
import com.example.java.anishop.repository.entity.Users;
import com.example.java.anishop.service.CartService;
import com.example.java.anishop.util.SecurityUtils;

public class CartServiceImpl implements CartService{

    @Autowired
    private MapperConverter cartConverter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private SecurityUtils securityUtils;
    @Override
    public ApiResponse<?> myCart(Long cartId) {
        String email=securityUtils.getCurrentUserEmail();
        Users user=userRepository.findByEmail(email)
                .orElseThrow(()-> new AppException("Email Không hợp lệ", 403));
        
        Carts carts=cartRepository.findById(cartId)
                .orElseThrow(()-> new AppException("Không tìm thấy giỏ hàng của bạn", 404));

        validateCartOwner(email, carts);

        CartDTO dto=cartConverter.setCartDTO(carts);

        return ApiResponse.<CartDTO>builder()
                .status(200)
                .message("Giỏ hàng của bạn")
                .data(dto)
                .build();
    }

    @Override
    public void validateCartOwner(String email, Carts carts) {
        if(!carts.getUserCarts().getEmail().equals(email)){
            throw new AppException("Không phải giỏ hàng của bạn. bạn kh có quyền truy cập",403);
        }
    }

}
