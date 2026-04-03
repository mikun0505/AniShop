package com.example.java.anishop.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.java.anishop.model.ProductDTO;
import com.example.java.anishop.model.ShopDTO;
import com.example.java.anishop.repository.entity.Products;
import com.example.java.anishop.repository.entity.Shops;

@Component
public class ProductConverter {

    @Autowired
    private ModelMapper model;

    public ProductDTO setProductDtO(Products products){
        ProductDTO productDTO=model.map(products,ProductDTO.class);

        return productDTO;
    }
    public ShopDTO setShopSTO(Shops shop){
        ShopDTO shops=model.map(shop, ShopDTO.class);
        return shops;
    }

}
