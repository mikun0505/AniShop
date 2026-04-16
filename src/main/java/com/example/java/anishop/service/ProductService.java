package com.example.java.anishop.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.request.ProductRequest;

@Service
public interface ProductService {
    ApiResponse<?> findById(Long id);
    ApiResponse<?> findAllProducts(Map<String,Object> params);
    // thêm sản phẩm
    ApiResponse<?> createdProduct(ProductRequest request);
    // update sản phẩm
    ApiResponse<?> updateProduct(ProductRequest request);
    // xóa sản phẩm
    ApiResponse<?> deletedProduct(Long shopId,Long productId);
}
