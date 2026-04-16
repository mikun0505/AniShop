package com.example.java.anishop.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.anishop.builder.ProductSearchBuilder;
import com.example.java.anishop.converter.MapperConverter;
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
    private MapperConverter productConverter;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SecurityUtils securityUtils;
    @Override
    public ApiResponse<?> findById(Long id) {
        try{
            List<Products> products=productRepository.findByProductIdAndDeletedFalse(id);
            if(products.isEmpty()){
                throw new AppException("Không tìm thấy sản phẩm",404);
            }
            List<ProductDTO> product=products.stream()
                    .map(it->{
                        ProductDTO dto=productConverter.setProductDtO(it);
                        dto.setCategoryId(it.getCaregori().getCaregoryId());
                        dto.setCategoryName(it.getCaregori().getCaregoryName());
                        return dto;
                    }).collect(Collectors.toList());
            return ApiResponse.<List<ProductDTO>>builder()
                        .status(200)
                        .message("Đã tìm thấy")
                        .data(product)
                        .build();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
       

    }

    @Override
    public ApiResponse<?> findAllProducts(Map<String, Object> params) {
        ProductSearchBuilder product=productSearchBuilderConveter.productSearchBuilder(params);
        List<Products> prodList=productRepository.findAllProducts(product);
        List<ProductDTO> productDTOs=new ArrayList<>();
        if(prodList!=null){
            for(Products it:prodList){
                ProductDTO productDTO=productConverter.setProductDtO(it);
                productDTO.setCategoryName(it.getCaregori().getCaregoryName());
                productDTO.setCategoryId(it.getCaregori().getCaregoryId());
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
        return ApiResponse.<List<ProductDTO>>builder()
                    .status(200)
                    .message("Đã tìm thấy")
                    .data(productDTOs)
                    .build();
    }

    @Override
    public ApiResponse<?> createdProduct(ProductRequest request) {
        String email=securityUtils.getCurrentUserEmail();
        Shops shop=shopRepository.findById(request.getShopId())
                    .orElseThrow(()-> new AppException("Shop not found",404));

        Caregories caregories=caregoriRepository.findById(request.getCaregoryId())
                .orElseThrow(()-> new AppException("Caregories not found",404));

        shopService.validateShopOwner(shop, email);

        Products product=new Products();
        product.setCaregori(caregories);
        product.setCreatedAt(LocalDateTime.now());
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setShopProduct(shop);
        product.setCaregori(caregories);
        product.setDescription(request.getDescription());
        product.setStock(request.getStock());
        product.setStatus(request.getStatus());
        productRepository.save(product);
        ProductDTO dto=productConverter.setProductDtO(product);
        return ApiResponse.<ProductDTO>builder()
                    .status(200)
                    .message("Đã thêm sản phẩm thành công!")
                    .data(dto)
                    .build();
    }

    @Override
    public ApiResponse<?> updateProduct(ProductRequest request) {
        String email=securityUtils.getCurrentUserEmail();
        Shops shop=shopRepository.findById(request.getShopId())
                    .orElseThrow(()-> new AppException("Shop not found",404));
        Products product=productRepository.findById(request.getProductId())
                        .orElseThrow(()-> new AppException("Không tìm thấy sản phẩm", 404));
        
        Caregories caregories=caregoriRepository.findById(request.getCaregoryId())
                        .orElseThrow(()-> new AppException("Không tìm thấy danh mục", 404));

                    
        if(request.getProductName()!=null){
            product.setProductName(request.getProductName());
        }
        if(request.getDescription()!=null){
            product.setDescription(request.getDescription());
        }
        if(request.getPrice()!=null){
            product.setPrice(request.getPrice());
        }
        if(request.getImgUrl()!=null){
            product.setProduct(request.getImgUrl().stream()
                .map( us->{
                    ProductImages img=new ProductImages();
                    img.setImageUrl(us);  // gán ảnh
                    img.setProducts(product);  // map ngược lại
                    return img;
                }).collect(Collectors.toSet()));
        }

        if(request.getStock()!=null){
            product.setStock(request.getStock());
        }
        if(request.getStatus()!=null){
            product.setStatus(request.getStatus());
        }

        productRepository.save(product);
        ProductDTO dto=productConverter.setProductDtO(product);

        return ApiResponse.<ProductDTO>builder()
                    .status(200)
                    .message("Đã update thành công")
                    .data(dto)
                    .build();
    }

    @Override
    public ApiResponse<?> deletedProduct(Long shopId,Long productId) {
       try{ String email=securityUtils.getCurrentUserEmail();

            Shops shop=shopRepository.findById(shopId)
                    .orElseThrow(()-> new AppException("Không tìm thấy shop",404));

        shopService.validateShopOwner(shop, email);

            Products product=productRepository.findById(productId)
                    .orElseThrow(()-> new AppException("Không tìm thấy sản phẩm để xóa", 404));
        
        product.setDeleted(true);
        productRepository.save(product);
        ProductDTO dto=productConverter.setProductDtO(product);

        return ApiResponse.<ProductDTO>builder()
                        .status(204)
                        .message("Đã xóa thành công")
                        .data(dto)
                        .build();
       }catch(Exception e){
            e.printStackTrace();
            throw e;
       }
    }

}
