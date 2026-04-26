package com.example.java.anishop.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemRequest {

    private Long cartItemId;

    @NotNull(message="cartId mục không được trống")
    @Min(value=1,message="Không được âm hay bằng 0")
    private Long userId;

    @NotNull(message="productId mục không được trống")
    @Min(value=1,message="Không được âm hay bằng 0")
    private Long productId;

    @NotNull(message="Số lượng không được trông")
    @Min(value=1,message="Số lượng không được âm hay bằng 0")
    private Long quantity;
}
