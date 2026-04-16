package com.example.java.anishop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.entity.OrderDetails;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Long>{
    // lấy danh sách orderDetails
    List<OrderDetails> findByOrderOrderIdAndDeletedFalse(Long orderId);
}
