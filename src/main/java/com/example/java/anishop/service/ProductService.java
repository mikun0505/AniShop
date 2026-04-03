package com.example.java.anishop.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.ProductDTO;

@Service
public interface ProductService {
    List<ProductDTO> findById(Long id);
    List<ProductDTO> findAll(Map<String,Object> params);
}
