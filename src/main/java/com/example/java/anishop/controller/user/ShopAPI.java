package com.example.java.anishop.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.anishop.model.ShopDTO;
import com.example.java.anishop.service.ShopService;



@RestController
public class ShopAPI {
    
    @Autowired
    private ShopService shopService;
    @GetMapping("/api/shops/")
    public ResponseEntity<List<ShopDTO>> getMethodName(@RequestParam String nameShop) {
        List<ShopDTO> result=shopService.findNameShops(nameShop);

        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/shops/{id}")
    public ShopDTO getShopId(@PathVariable Long id) {
        ShopDTO shops=shopService.findById(id);
        return shops;
    }
    
}
