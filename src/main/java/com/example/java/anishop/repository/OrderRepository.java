package com.example.java.anishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.entity.Orders;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    
}
