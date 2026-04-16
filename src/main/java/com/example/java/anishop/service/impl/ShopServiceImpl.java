package com.example.java.anishop.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.anishop.converter.MapperConverter;
import com.example.java.anishop.exception.AppException;
import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.reponse.ShopDTO;
import com.example.java.anishop.model.request.ShopRequest;
import com.example.java.anishop.repository.ShopRepository;
import com.example.java.anishop.repository.UserRepository;
import com.example.java.anishop.repository.entity.Shops;
import com.example.java.anishop.repository.entity.Users;
import com.example.java.anishop.service.ShopService;
import com.example.java.anishop.util.SecurityUtils;


@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityUtils security;
    @Autowired
    private MapperConverter shopConveter;
    @Autowired
    private ShopRepository shopRepository;
    @Override
    public List<ShopDTO> findByNameShopsAndDeletedFalse(String nameShop) {
        List<ShopDTO> shops=shopRepository.findByNameShop(nameShop).stream()
        .map(new Function<Shops, ShopDTO>() {
            @Override
            public ShopDTO apply(Shops s) {
                ShopDTO shop=shopConveter.setShopDTO(s);
                // shop.setShopId(s.getShopId());
                // shop.setShopName(s.getNameShop());
                // shop.setLogo(s.getLogo());
                // shop.setIsActive(s.getIsActive());

                
                return shop;
            }
        }).collect(Collectors.toList());

        return shops;
    }
    @Override
    public ShopDTO findByIdAndDeletedFalse(Long id) {
        Shops shops=shopRepository.findByShopIdAndDeletedFalse(id).get();
        return shopConveter.setShopDTO(shops);
    }

    @Override
    public List<ShopDTO> myShop() {
        String email=security.getCurrentUserEmail();
        return shopRepository.findByUserShopEmailAndDeletedFalse(email).stream()
                    .map(shopConveter::setShopDTO)  // map ShopDTO kiểu trả về dạng List<ShopDTO>
                    .collect(Collectors.toList());
    }

    @Override
    public ApiResponse<?> createdShop(ShopRequest shop) {
        String email=security.getCurrentUserEmail();
        Users user=userRepository.findByEmail(email)
            .orElseThrow(()-> new AppException("User not found",404));

        Shops s=new Shops();
        s.setCreatedAt(LocalDateTime.now());
        s.setNameShop(shop.getShopName());
        s.setLogo(shop.getLogo());
        s.setUserShop(user);
        s.setDescription(shop.getDescription());
        s.setIsActive(true);
        shopRepository.save(s);

        return ApiResponse.<ShopRequest>builder()
                .status(201)
                .message("Đã tạo thành công.")
                .data(shop)
                .build();
    }

    @Override
    public ApiResponse<?> updateShop(Long id, ShopRequest request) {
        
        Shops shop=shopRepository.findById(id)
                .orElseThrow(()-> new AppException("Shop not Found",404));

        if(request.getShopName()!=null){
            shop.setNameShop(request.getShopName());
        }
        if(request.getDescription()!=null){
            shop.setDescription(request.getDescription());
        }
        if(request.getLogo()!=null){
            shop.setLogo(request.getLogo());
        }
        shopRepository.save(shop);
        ShopDTO dto=shopConveter.setShopDTO(shop);
        return ApiResponse.<ShopDTO>builder()
                .status(200)
                .message("Đã cập nhật thành công.")
                .data(dto)
                .build();
    }

    @Override
    public void validateShopOwner(Shops shop, String email) {
        if(!shop.getUserShop().getEmail().equals(email)){
            throw new AppException("Bạn không có quyền để thao tác.",403);
        }
    }

    @Override
    public ApiResponse<?> toggleShopStatus(Long id) {
        String email=security.getCurrentUserEmail();

        Shops shop=shopRepository.findById(id)
                .orElseThrow(()-> new AppException("Shops not found", 404));
   
        validateShopOwner(shop, email);

        shop.setIsActive(!shop.getIsActive());
        ShopDTO dto=shopConveter.setShopDTO(shop);
        return ApiResponse.<ShopDTO>builder()
                    .status(200)
                    .message("Đã thây đổi trạng thái thành công.")
                    .data(dto)
                    .build();
    }

    @Override
    public ApiResponse<?> deteleShop(Long id) {
        String email=security.getCurrentUserEmail();
        Shops shop=shopRepository.findById(id)
                .orElseThrow(()-> new AppException("Shop not found", 404));

        validateShopOwner(shop, email);

        shop.setDeleted(true);
        shop.setIsActive(false);
        shopRepository.save(shop);

        return ApiResponse.<String>builder()
                .status(200)
                .message("Đã xóa thành công.")
                .data(shop.getNameShop())
                .build();
    }



}
