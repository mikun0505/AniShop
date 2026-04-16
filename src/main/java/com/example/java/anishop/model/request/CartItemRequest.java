package com.example.java.anishop.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemRequest {

    private Long cartItemId;

    @NotNull(message="cartId mục không được trống")
    @Size(min=1,message="Không được âm hay bằng 0")
    private Long userId;

    @NotNull(message="productId mục không được trống")
    @Size(min=1,message="Không được âm hay bằng 0")
    private Long productId;

    @NotNull(message="Số lượng không được trông")
    @Size(min=1,message="Số lượng không được âm hay bằng 0")
    private Long quantity;
}
