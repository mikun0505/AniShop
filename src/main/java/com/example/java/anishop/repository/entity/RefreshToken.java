package com.example.java.anishop.repository.entity;

import java.time.LocalDateTime;


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
@Table(name="refresh_token")
public class RefreshToken {  // Mã đựng Token
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    // @Column(name="user_id")
    // private Long userId;

    @Column(name="token" , unique=true)
    private String token;

    @Column(name="expiry_date")
    private LocalDateTime expiryDate;
    
    @ManyToOne
    @JoinColumn(name="user_id",insertable=false,updatable=false)
    private Users user;

}
