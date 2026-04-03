package com.example.java.anishop.builder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductSearchBuilder {
    
    private Long productId;
    private String productName;
    private Long shopId;
    private Double priceMax;
    private Double priceMin;
    private Long categoryId;
    private Boolean status;


    // private Long productId;
    // private String productName;
    // private Long shopId;
    // private Double priceMin;
    // private Double priceMax;
    // private Long categoryId;
    // private Boolean status;

    // // Getter
    // public Long getProductId() { return productId; }
    // public String getProductName() { return productName; }
    // public Long getShopId() { return shopId; }
    // public Double getPriceMin() { return priceMin; }
    // public Double getPriceMax() { return priceMax; }
    // public Long getCategoryId() { return categoryId; }
    // public Boolean getStatus() { return status; }

    // // Constructor private - chỉ Builder mới tạo được
    // private ProductSearchBuilder(Builder builder) {
    //     this.productId   = builder.productId;
    //     this.productName = builder.productName;
    //     this.shopId      = builder.shopId;
    //     this.priceMin    = builder.priceMin;
    //     this.priceMax    = builder.priceMax;
    //     this.categoryId  = builder.categoryId;
    //     this.status      = builder.status;
    // }

    // // Static Builder class
    // public static class Builder {

    //     private Long productId;
    //     private String productName;
    //     private Long shopId;
    //     private Double priceMin;
    //     private Double priceMax;
    //     private Long categoryId;
    //     private Boolean status;

    //     public Builder productId(Long productId) {
    //         this.productId = productId;
    //         return this;
    //     }
    //     public Builder productName(String productName) {
    //         this.productName = productName;
    //         return this;
    //     }
    //     public Builder shopId(Long shopId) {
    //         this.shopId = shopId;
    //         return this;
    //     }
    //     public Builder priceMin(Double priceMin) {
    //         this.priceMin = priceMin;
    //         return this;
    //     }
    //     public Builder priceMax(Double priceMax) {
    //         this.priceMax = priceMax;
    //         return this;
    //     }
    //     public Builder categoryId(Long categoryId) {
    //         this.categoryId = categoryId;
    //         return this;
    //     }
    //     public Builder status(Boolean status) {
    //         this.status = status;
    //         return this;
    //     }

    //     // Build ra object ProductSearchBuilder
    //     public ProductSearchBuilder build() {
    //         return new ProductSearchBuilder(this);
    //     }
    // }

}

