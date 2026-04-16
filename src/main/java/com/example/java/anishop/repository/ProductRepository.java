package com.example.java.anishop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.custom.ProductCustomRepository;
import com.example.java.anishop.repository.entity.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long>, ProductCustomRepository{
    List<Products> findByProductIdAndDeletedFalse(Long id);
}
