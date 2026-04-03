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
@Table(name="product_images")
public class ProductImages {  //Hình ảnh săn phẩm
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long productImageId;

    // @Column(name="product_id")
    // private Long productId;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="is_thumbnail")
    private Boolean isThumbnail;

    @ManyToOne
    @JoinColumn(name="product_id",insertable=false,updatable = false)
    private Products products;
}
