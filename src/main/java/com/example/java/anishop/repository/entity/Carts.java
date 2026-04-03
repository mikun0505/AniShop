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
@Table(name="carts")
public class Carts {  // Giỏ hàng

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long cartsId;

    // @Column(name="user_id")
    // private Long userId;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="user_id",insertable=false,updatable=false)
    private Users userCarts;

    @OneToMany(mappedBy="cart",fetch=FetchType.LAZY)
    private Set<CartItems> cartItems=new HashSet<>();
}
