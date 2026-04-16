package com.example.java.anishop.repository.entity;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

@Setter
@Getter
@Entity
@Table(name="shops")
public class Shops {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long shopId;

    // @Column(name="user_id", insertable=false, updatable=false)
    // private Long userId;


    @Column(name="name_shop")
    private String nameShop;

    @Column(name="description")
    private String description;

    @Column(name="logo")
    private String logo;

    @Column(name="is_active")
    private Boolean isActive;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name="update_at")
    private LocalDateTime updateAt;
    @Column(name="deleted")
    private Boolean deleted=false;
    @ManyToOne
    @JoinColumn(name="user_id")
    private Users userShop;


    @OneToMany(mappedBy="shopProduct",fetch=FetchType.LAZY)
    private Set<Products> ProductShop=new HashSet<>();
}
