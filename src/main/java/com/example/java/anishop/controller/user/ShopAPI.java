package com.example.java.anishop.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.reponse.ShopDTO;
import com.example.java.anishop.model.request.ShopRequest;
import com.example.java.anishop.service.ShopService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;




@RestController
public class ShopAPI {
    
    @Autowired
    private ShopService shopService;
    @GetMapping("/api/shops")
    public ResponseEntity<List<ShopDTO>> getMethodName(@RequestParam String nameShop) {
        List<ShopDTO> result=shopService.findByNameShopsAndDeletedFalse(nameShop);

        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/shops/{id}")
    public ShopDTO getShopId(@PathVariable Long id) {
        ShopDTO shops=shopService.findByIdAndDeletedFalse(id);
        return shops;
    }
    
    @PostMapping("/api/shops")
    public ResponseEntity<ApiResponse<?>> putMethodName(@Valid @RequestBody ShopRequest request) {
        return ResponseEntity.ok(shopService.createdShop(request));   
        
    }

    @PutMapping("/api/shops/{shopId}")
    public ResponseEntity<ApiResponse<?>> putMethodName(@PathVariable @Min(1) Long shopId,
                                                    @Valid @RequestBody ShopRequest entity) {
        
        return ResponseEntity.ok(shopService.updateShop(shopId, entity));
    }

    @DeleteMapping("/api/shops/{id}")
    public ResponseEntity<ApiResponse<?>> deleteShops(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(shopService.deteleShop(id));
    }
    // update 1 phần
    @PatchMapping("/api/{shopId}/toggle")
    public ResponseEntity<ApiResponse<?>> toggleShop(@PathVariable @Min(1) Long shopId){
        return ResponseEntity.ok(shopService.toggleShopStatus(shopId));
    }
}
