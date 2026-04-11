package com.example.java.anishop.controller.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.anishop.model.reponse.ProductDTO;
import com.example.java.anishop.service.ProductService;



@RestController
public class ProductAPI {

    @Autowired
    private ProductService productService;
    
    // @GetMapping("/api/products/")
    // public List<ProductDTO> getMethodName(@RequestParam Long id) {
    //     List<ProductDTO> products=productService.findById(id);
    //     return products;
    // }
    @GetMapping("/api/products/")
    public List<ProductDTO> getMethodName(@RequestParam Map<String,Object> params) {
        List<ProductDTO> result=productService.findAll(params);
        return result;
    }
    
}
