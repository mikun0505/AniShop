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

@Setter
@Getter
@Entity
@Table(name="order_details")
public class OrderDetails {  // Chi tiết đơn hàng
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long orderDetailId;

    // @Column(name="order_id")
    // private Long orderId;

    // @Column(name="product_id")
    // private Long productId;

    @Column(name="price")
    private Double price;

    @Column(name="quantity")
    private Long quantity;

    @ManyToOne
    @JoinColumn(name="order_id",insertable=false,updatable=false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name="product_id",insertable=false,updatable=false)
    private Products productOrderDetail;
}
