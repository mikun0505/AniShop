package com.example.java.anishop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.entity.Orders;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    // @Query(
    //         "SELECT DISTINCT o FROM Orders o " +
    //         "JOIN o.orderDetail oi "+
    //         "JOIN oi.productOrderDetail p " 
    //         +"WHERE p.shopId = :shopId"
    //         )
    // List<Orders> orders(@Param("shopId") Long shopId);
    void deleteByUserOrder_Id(Long id);


    // tìm xem thử có đúng ng dùng đặt hàng kh
    List<Orders> findByUserOrderIdAndDeletedFalse(Long userId);
}
