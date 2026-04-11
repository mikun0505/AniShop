package com.example.java.anishop.model.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopDTO {

    private Long shopId;
    private String nameShop;
    private String description;
    private String logo;
    private Boolean isActive;
    private UserDTO userDTO;
}
