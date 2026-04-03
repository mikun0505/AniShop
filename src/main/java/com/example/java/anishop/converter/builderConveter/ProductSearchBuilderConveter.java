package com.example.java.anishop.converter.builderConveter;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.java.anishop.builder.ProductSearchBuilder;
import com.example.java.anishop.util.MapUtils;

@Component
public class ProductSearchBuilderConveter {
    public ProductSearchBuilder productSearchBuilder(Map<String,Object> params){
        ProductSearchBuilder productBuilder=ProductSearchBuilder.builder()
        .productId(MapUtils.getObect(params, "productId", Long.class))
        .productName(MapUtils.getObect(params, "productName", String.class))
        .shopId(MapUtils.getObect(params, "shopId", Long.class))
        .priceMax(MapUtils.getObect(params, "priceMax", Double.class))
        .priceMin(MapUtils.getObect(params, "priceMin", Double.class))
        .categoryId(MapUtils.getObect(params, "categoryId", Long.class))
        .status(MapUtils.getObect(params, "status", Boolean.class))
        .build();

        return productBuilder;
    }
   
}
