package com.example.java.anishop.repository.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="orders")
public class Orders {  //Đơn hàng
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long orderId;

    // @Column(name="user_id")
    // private Long userId;

    @Column(name="total_price")
    private Long totalPrice;

    @Column(name="address")
    private String address;

    @Column(name="status")
    private Boolean status;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="user_id",insertable=false,updatable=false)
    private Users userOrder;

    @OneToMany(mappedBy="order",fetch=FetchType.LAZY)
    private Set<OrderDetails> orderDetail=new HashSet<>();
    
}
