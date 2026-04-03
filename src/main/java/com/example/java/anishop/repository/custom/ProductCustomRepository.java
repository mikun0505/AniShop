package com.example.java.anishop.repository.custom;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.builder.ProductSearchBuilder;
import com.example.java.anishop.repository.entity.Products;

@Repository
public interface ProductCustomRepository {
    @Query(value="""
    SELECT p.product_id, p.product_name, p.shop_id, 
           p.category_id, p.price, p.status ,p.description,p.stock
    FROM products p
    """, nativeQuery=true
)
    List<Products> findAll(ProductSearchBuilder productSearchBuilder);
}
