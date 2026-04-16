package com.example.java.anishop.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
    
    @NotNull(message="Không được bỏ trống")
    @Size(min=1,message="Id không được âm or bằng 0")
    private Long cartId;
    
}
