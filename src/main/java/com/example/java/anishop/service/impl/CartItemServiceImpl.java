package com.example.java.anishop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.anishop.converter.MapperConverter;
import com.example.java.anishop.exception.AppException;
import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.reponse.CartDTO;
import com.example.java.anishop.model.reponse.CartItemDTO;
import com.example.java.anishop.model.reponse.ProductDTO;
import com.example.java.anishop.model.request.CartItemRequest;
import com.example.java.anishop.repository.CartItemRepository;
import com.example.java.anishop.repository.CartRepository;
import com.example.java.anishop.repository.ProductRepository;
import com.example.java.anishop.repository.entity.CartItems;
import com.example.java.anishop.repository.entity.Carts;
import com.example.java.anishop.repository.entity.Products;
import com.example.java.anishop.service.CartItemService;

import jakarta.transaction.Transactional;

@Service
public class CartItemServiceImpl implements CartItemService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private MapperConverter productConverter;
    @Override
    public ApiResponse<?> getCartItem(Long id) {
        Carts carts=cartRepository.findByUserCartsIdAndDeletedFalse(id)
                .orElseThrow(()-> new AppException("Không tìm thấy giỏ hàng", 404));

        List<CartItems> cartItems=cartItemRepository.findByCartCartsIdAndDeletedFalse(id);
        if(cartItems.isEmpty()){
            throw new AppException("Không có sản phẩm trong giỏ hàng", 404);
        }

        List<CartItemDTO> dto=cartItems.stream()
                            .map(item-> productConverter.setCartItemDTO(item))
                            .collect(Collectors.toList());

        return ApiResponse.<List<CartItemDTO>>builder()
                    .status(200)
                    .message("Tìm thấy")
                    .data(dto)
                    .build();
        
    }

    @Transactional
    @Override
    public ApiResponse<?> addCartItem(CartItemRequest request) {
       
        Carts carts=cartRepository.findByUserCartsIdAndDeletedFalse(request.getUserId())
                    .orElseThrow(()-> new AppException("Không thể truy cập",403));

        Products products=productRepository.findById(request.getProductId())
                .orElseThrow(()-> new AppException("Không tìm thấy sản phẩm", 404));
                
        CartItems cartItem=cartItemRepository.findByCartCartsIdAndCartProductProductIdAndDeletedFalse(carts.getCartsId(),request.getProductId())
                    .map(it->{
                        it.setQuantity(it.getQuantity()+request.getQuantity());
                        return cartItemRepository.save(it); 
                    }).orElseGet(()->{
                        CartItems items=new CartItems();
                        items.setCart(carts);
                        items.setCartProduct(products);
                        items.setQuantity(request.getQuantity());
                        return cartItemRepository.save(items);
                    });
        CartDTO cartDTO=productConverter.setCartDTO(carts);
        ProductDTO productDTO=productConverter.setProductDtO(products);
        List<ProductDTO> productDTOs=new ArrayList<>();
        productDTOs.add(productDTO);
        CartItemDTO dto=productConverter.setCartItemDTO(cartItem);
        dto.setCartDTO(cartDTO);
        dto.setCartProductDTO(productDTOs);
        return ApiResponse.<CartItemDTO>builder()
                    .status(201)
                    .message("Đã thêm thành công")
                    .data(dto)
                    .build();
            
    }
    
    @Transactional
    @Override
    public ApiResponse<?> updateCartItem(CartItemRequest request) {
        Carts carts=cartRepository.findByUserCartsIdAndDeletedFalse(request.getUserId())
            .orElseThrow(()-> new AppException("Không thể truy cập",403));

    Products products=productRepository.findById(request.getProductId())
        .orElseThrow(()-> new AppException("Không tìm thấy sản phẩm", 404));
        CartItems cartItems=cartItemRepository.findById(request.getCartItemId())
                    .orElseThrow(()-> new AppException("không tìm thấy sản phẩm trong giỏ hàng", 404));
        if(request.getQuantity()<=0){
            cartItems.setDeleted(true);
        }else{
            cartItems.setQuantity(request.getQuantity());
        }

        CartItemDTO dto=productConverter.setCartItemDTO(cartItems);
        return ApiResponse.<CartItemDTO>builder()
                    .status(200)
                    .message("Đã update thành công")
                    .data(dto)
                    .build();

        
    }
    
    @Transactional
    @Override
    public ApiResponse<?> deleteCartItemId(Long cartItemId) {
        CartItems cartItems=cartItemRepository.findById(cartItemId)
                    .orElseThrow(()-> new AppException("Không tìm thấy sản phẩm trong giỏ hàng", 404));
        cartItems.setDeleted(true);
        cartItemRepository.save(cartItems);

        return ApiResponse.<String>builder()
                    .status(204)
                    .message("Đã xáo thành công")
                    .data(null)
                    .build();
    }
    
    @Transactional
    @Override
    public ApiResponse<?> deleteAllCarItem(Long urserId) {
        Carts carts=cartRepository.findByUserCartsIdAndDeletedFalse(urserId)
                .orElseThrow(()-> new AppException("Không tìm thấy giỏ hàng", 404));

        List<CartItems> cartItems=cartItemRepository.findByCartCartsIdAndDeletedFalse(urserId);
        
        cartItems.forEach(it->it.setDeleted(true));

        cartItemRepository.saveAll(cartItems);
        
        return ApiResponse.<String>builder()
            .status(204)
            .message("Đã xáo thành công")
            .data(null)
            .build();
    }

}
