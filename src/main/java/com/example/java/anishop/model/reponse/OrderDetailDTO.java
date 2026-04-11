package com.example.java.anishop.model.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDTO {
    private Long orderDetailId;
    private Long quantity;
    private Double price;
    private String productName;
    private String productImage;
}
