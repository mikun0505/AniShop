package com.example.java.anishop.repository.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="cart_items")
public class CartItems {   // Món hàng trong giỏ

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long cartItemId;

    // @Column(name="cart_id")
    // private Long cartsId;

    // @Column(name="product_id")
    // private Long productId;

    @Column(name="quantity")
    private Long quantity;
    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="update_at")
    private LocalDateTime updateAt;
    @Column(name="deleted")
    private Boolean deleted=false;
    @ManyToOne
    @JoinColumn(name="cart_id")
    private Carts cart;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Products cartProduct;
}
