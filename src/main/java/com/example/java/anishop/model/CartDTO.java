package com.example.java.anishop.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartDTO {
    private Long cartId;
    private UserDTO cartUserDTO;
    private LocalDateTime createdAt;
}
