package com.example.java.anishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.entity.CartItems;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long>{

}
