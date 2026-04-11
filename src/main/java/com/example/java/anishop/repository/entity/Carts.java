package com.example.java.anishop.repository.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

  
    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="update_at")
    private LocalDateTime updateAt;
    @Column(name="deleted")
    private Boolean deleted=false;
    @OneToOne
    @JoinColumn(name="user_id")
    private Users userCarts;

    @OneToMany(mappedBy="cart",fetch=FetchType.LAZY)
    private Set<CartItems> cartItems=new HashSet<>();
}
