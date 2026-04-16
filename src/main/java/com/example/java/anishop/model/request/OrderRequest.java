package com.example.java.anishop.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private Long orderId;

    @NotBlank(message="Status không đc trống")
    private String status;

    @NotNull(message="shop không được trống")
    @Size(min=1,message="Không dc âm")
    private Long shopId;
}
