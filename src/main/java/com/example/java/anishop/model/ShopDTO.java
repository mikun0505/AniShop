package com.example.java.anishop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopDTO {

    private Long shopId;
    private String shopName;
    private String description;
    private String logo;
    private Boolean IsActive;
    private UserDTO userDTO;
}
