package com.example.java.anishop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.anishop.builder.ProductSearchBuilder;
import com.example.java.anishop.converter.ProductConverter;
import com.example.java.anishop.converter.builderConveter.ProductSearchBuilderConveter;
import com.example.java.anishop.model.OrderDetailDTO;
import com.example.java.anishop.model.ProductDTO;
import com.example.java.anishop.repository.ProductRepository;
import com.example.java.anishop.repository.custom.ProductCustomRepository;
import com.example.java.anishop.repository.entity.ProductImages;
import com.example.java.anishop.repository.entity.Products;
import com.example.java.anishop.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductCustomRepository productCustomRepository;
    @Autowired
    private ProductSearchBuilderConveter productSearchBuilderConveter;
    // @Autowired
    // private ProductSearchBuilder productSearchBuilder;
    @Autowired
    private ProductConverter productConverter;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<ProductDTO> findById(Long id) {
        List<ProductDTO> product=new ArrayList<>();
        Products products=productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found"));
        product.add(productConverter.setProductDtO(products));
        return product;
       

    }

    @Override
    public List<ProductDTO> findAll(Map<String, Object> params) {
        ProductSearchBuilder product=productSearchBuilderConveter.productSearchBuilder(params);
        List<Products> prodList=productRepository.findAll(product);
        List<ProductDTO> productDTOs=new ArrayList<ProductDTO>();
        if(prodList!=null){
            for(Products it:prodList){
                ProductDTO productDTO=productConverter.setProductDtO(it);
                productDTO.setCategoryName(it.getCaregori().getCaregoriName());
                productDTO.setCategoryId(it.getCaregori().getCaregorieId());
                List<OrderDetailDTO> productOrderDetails=it.getOrderDetailProduct().stream()  // chuyển set -> list
                .map(od ->{
                    OrderDetailDTO dto=new OrderDetailDTO();    // đẩy DTO vào để set vào ProductDTO do nó nhận OrderDetailsDTO
                    dto.setOrderDetailId(od.getOrderDetailId());
                    dto.setQuantity(od.getQuantity());
                    dto.setPrice(od.getPrice());

                    return dto;

                }).collect(Collectors.toList());  // trả về List
                productDTO.setProductOrderDetail(productOrderDetails);  // set vào
                List<String> img=it.getProduct().stream()
                .map(ProductImages::getImageUrl)
                .collect(Collectors.toList());
                productDTO.setImgUrl(img);
                productDTOs.add(productDTO);
                

            }
        }
        return productDTOs;
    }

}
