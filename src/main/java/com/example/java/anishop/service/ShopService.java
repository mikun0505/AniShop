package com.example.java.anishop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.ShopDTO;



@Service
public interface ShopService {
    List<ShopDTO> findNameShops(String nameShop);
    ShopDTO findById(Long id);
}
