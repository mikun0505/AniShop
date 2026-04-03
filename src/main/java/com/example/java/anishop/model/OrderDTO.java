package com.example.java.anishop.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {
    private Long orderId;
    private UserDTO orderUserDTO;
    private String address;
    private Long totalPrice;
    private Boolean status;
    private LocalDateTime createdAt;
    private List<OrderDetailDTO> orderDetail;
}
