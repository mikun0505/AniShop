package com.example.java.anishop.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private Long cartItemId;
    private Long quantity;
    private CartDTO cartDTO;
    private List<ProductDTO> cartProductDTO;
}
