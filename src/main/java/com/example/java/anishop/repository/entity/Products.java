package com.example.java.anishop.repository.entity;


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
@Table(name="products")
public class Products {  // Sản phẩm
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long productId;

    @Column(name="product_name")
    private String productName;

    // @Column(name="category_id")
    // private Long categoryId;

    // @Column(name="shop_id")
    // private Long ShopId;

    @Column(name="price")
    private Double price;

    @Column(name="description")
    private String description;

    @Column(name="stock")
    private Long stock;

    @Column(name="status")
    private Boolean status;
    
    @OneToMany(mappedBy="products",fetch=FetchType.LAZY)
    private Set<ProductImages> product=new HashSet<>();

    @ManyToOne
    @JoinColumn(name="category_id",insertable=false,updatable=false)
    private Caregories caregori;

    @ManyToOne
    @JoinColumn(name="shop_id",insertable=false,updatable=false)
    private Shops shopProduct;
    @OneToMany(mappedBy="cartProduct",fetch=FetchType.LAZY)
    private Set<CartItems> cartItemProduct=new HashSet<>();

    @OneToMany(mappedBy="productOrderDetail",fetch=FetchType.LAZY)
    private Set<OrderDetails> orderDetailProduct=new HashSet<>();
}
