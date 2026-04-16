package com.example.java.anishop.repository.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.java.anishop.builder.ProductSearchBuilder;
import com.example.java.anishop.repository.entity.Products;

@Repository
public interface ProductCustomRepository {
    
    List<Products> findAllProducts(ProductSearchBuilder productSearchBuilder);
}
