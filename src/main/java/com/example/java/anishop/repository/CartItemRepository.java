package com.example.java.anishop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.entity.CartItems;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long>{
    // lấy danh sách sản phẩm ch xóa còn trong giỏ hàng
    List<CartItems> findByCartCartsIdAndDeletedFalse(Long id);
     //tìm sản phẩm trong giỏ hàng của mình
    Optional<CartItems> findByCartCartsIdAndCartProductProductIdAndDeletedFalse(Long cartId,Long productId);
}
