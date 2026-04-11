package com.example.java.anishop.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopRequest {

    @NotBlank(message="Tên không được trống")
    private String shopName;

    private String logo;

    private String description;
}
