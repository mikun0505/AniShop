package com.example.java.anishop.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.request.ProductRequest;
import com.example.java.anishop.service.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;




@RestController
public class ProductAPI {

    @Autowired
    private ProductService productService;
    

    // tìm theo Id
    @GetMapping("/api/products/{id}")
    public ResponseEntity<ApiResponse<?>> getProductId(@PathVariable Long id) {
        System.out.println(">>> getById called, id = " + id);
        return ResponseEntity.ok(productService.findById(id));
    }

    // tìm tất cả
    @GetMapping("/api/products")
    public ResponseEntity<ApiResponse<?>> getMethodName(@RequestParam Map<String,Object> params) {
        return ResponseEntity.ok(productService.findAllProducts(params));
    }
    
    // thêm sản phẩm
    @PostMapping("/api/products")
    public ResponseEntity<ApiResponse<?>> createdProduct(@Valid @RequestBody ProductRequest request) {
        //TODO: process POST request
        return ResponseEntity.ok(productService.createdProduct(request));
    }
    

    // sửa sản phẩm

    @PutMapping("/api/products")
    public ResponseEntity<ApiResponse<?>> updatedProduct(@Valid @RequestBody ProductRequest request) {
        //TODO: process POST request
        
        return ResponseEntity.ok(productService.updateProduct(request));
    }

    //xóa sản phẩm  Delete kh hỗ trợ RequestBody
    @DeleteMapping("/api/products/{productId}")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@RequestParam @Min(1) Long shopId,
                                                        @PathVariable @Min(1) Long productId){
        return ResponseEntity.ok(productService.deletedProduct(shopId, productId));
    }
    
}
