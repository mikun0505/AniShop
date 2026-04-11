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

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="update_at")
    private LocalDateTime updateAt;
    @Column(name="deleted")
    private Boolean deleted=false;
    @ManyToOne
    @JoinColumn(name="order_id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Products productOrderDetail;
}
