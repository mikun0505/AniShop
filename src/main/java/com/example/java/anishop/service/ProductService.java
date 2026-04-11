package com.example.java.anishop.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.reponse.ProductDTO;
import com.example.java.anishop.model.request.ProductRequest;

@Service
public interface ProductService {
    List<ProductDTO> findById(Long id);
    List<ProductDTO> findAll(Map<String,Object> params);
    // thêm sản phẩm
    ApiResponse<?> createdProduct(ProductRequest request);
    // update sản phẩm
    ApiResponse<?> updateProduct(ProductRequest request);
}
