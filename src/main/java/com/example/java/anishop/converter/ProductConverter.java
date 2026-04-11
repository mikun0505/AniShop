package com.example.java.anishop.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.java.anishop.model.reponse.CaregoryDTO;
import com.example.java.anishop.model.reponse.ProductDTO;
import com.example.java.anishop.model.reponse.ShopDTO;
import com.example.java.anishop.model.reponse.UserDTO;
import com.example.java.anishop.repository.entity.Caregories;
import com.example.java.anishop.repository.entity.Products;
import com.example.java.anishop.repository.entity.Shops;
import com.example.java.anishop.repository.entity.Users;

@Component
public class ProductConverter {

    @Autowired
    private ModelMapper model;

    public ProductDTO setProductDtO(Products products){
        ProductDTO productDTO=model.map(products,ProductDTO.class);

        return productDTO;
    }
    public ShopDTO setShopDTO(Shops shop){
        ShopDTO shops=model.map(shop, ShopDTO.class);
        Users user=shop.getUserShop();
        if(user!=null){
                UserDTO us=new UserDTO();
                us.setUserId(user.getId());
                System.out.println(us.getUserId());
                us.setFullName(user.getFullName());
                shops.setUserDTO(us);

            }

        return shops;
    }

    public CaregoryDTO setCaregoryDTO(Caregories caregories){
        CaregoryDTO dto=model.map(caregories, CaregoryDTO.class);
        
        return dto;
    }
}
