package com.example.java.anishop.repository.entity;

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

    @ManyToOne
    @JoinColumn(name="cart_id",insertable=false,updatable = false)
    private Carts cart;

    @ManyToOne
    @JoinColumn(name="product_id",insertable=false,updatable = false)
    private Products cartProduct;
}
