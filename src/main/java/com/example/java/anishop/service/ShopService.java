package com.example.java.anishop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.reponse.ShopDTO;
import com.example.java.anishop.model.request.ShopRequest;
import com.example.java.anishop.repository.entity.Shops;



@Service
public interface ShopService {
    List<ShopDTO> findByNameShopsAndDeletedFalse(String nameShop);
    // tiềm shop ch bị xóa
    ShopDTO findByIdAndDeletedFalse(Long id);
    // shop của t
    List<ShopDTO>  myShop();    
    // kiểm tra xem users của ng dùng có đúng với shop đó hay kh
    void validateShopOwner(Shops shop,String email);
    ApiResponse<?> createdShop(ShopRequest shop);
    ApiResponse<?> updateShop(Long id,ShopRequest request);
    // bật tắt shop
    ApiResponse<?> toggleShopStatus(Long id);
    //xóa shop
    ApiResponse<?> deteleShop(Long id);
}
