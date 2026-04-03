package com.example.java.anishop.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long productId;
    private String productName;
    private Long categoryId;
    private Long ShopId;
    private Double price;
    private String description;
    private Long stock;
    private Boolean status;
    private List<String> imgUrl;
    private String shopName;
    private String categoryName;
    private List<OrderDetailDTO> productOrderDetail;
}
