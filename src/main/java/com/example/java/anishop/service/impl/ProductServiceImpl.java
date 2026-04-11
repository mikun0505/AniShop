package com.example.java.anishop.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.anishop.builder.ProductSearchBuilder;
import com.example.java.anishop.converter.ProductConverter;
import com.example.java.anishop.converter.builderConveter.ProductSearchBuilderConveter;
import com.example.java.anishop.exception.AppException;
import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.reponse.OrderDetailDTO;
import com.example.java.anishop.model.reponse.ProductDTO;
import com.example.java.anishop.model.request.ProductRequest;
import com.example.java.anishop.repository.CaregoriRepository;
import com.example.java.anishop.repository.ProductRepository;
import com.example.java.anishop.repository.ShopRepository;
import com.example.java.anishop.repository.entity.Caregories;
import com.example.java.anishop.repository.entity.ProductImages;
import com.example.java.anishop.repository.entity.Products;
import com.example.java.anishop.repository.entity.Shops;
import com.example.java.anishop.service.ProductService;
import com.example.java.anishop.service.ShopService;
import com.example.java.anishop.util.SecurityUtils;


@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private CaregoriRepository caregoriRepository;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopRepository shopRepository;
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

    @Override
    public ApiResponse<?> createdProduct(ProductRequest request) {
        String email=SecurityUtils.getCurrentUserEmail();
        Shops shop=shopRepository.findById(request.getShopId())
                    .orElseThrow(()-> new AppException("Shop not found",404));

        Caregories caregories=caregoriRepository.findById(request.getCarigoruId())
                .orElseThrow(()-> new AppException("Caregories not found",404));

        shopService.validateShopOwner(shop, email);

        Products product=productRepository.findById(request.getProductId())
                    .orElseThrow(()-> new AppException("Product not found", 404));
        
        product.setCaregori(caregories);
        product.setCreatedAt(LocalDateTime.now());
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setShopProduct(shop);
        product.setStock(request.getStock());
        product.setStatus(request.getStatus());
        ProductDTO dto=productConverter.setProductDtO(product);
        return ApiResponse.<ProductDTO>builder()
                    .status(200)
                    .message("Đã thêm sản phẩm thành công!")
                    .data(dto)
                    .build();
    }

    @Override
    public ApiResponse<?> updateProduct(ProductRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
