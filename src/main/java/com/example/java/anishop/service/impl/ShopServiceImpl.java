package com.example.java.anishop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.anishop.model.ShopDTO;
import com.example.java.anishop.model.UserDTO;
import com.example.java.anishop.repository.ShopRepository;
import com.example.java.anishop.service.ShopService;


@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;
    @Override
    public List<ShopDTO> findNameShops(String nameShop) {
        List<ShopDTO> shops=shopRepository.findByNameShop(nameShop).stream()
        .map( s ->{
            ShopDTO shop=new ShopDTO();
            UserDTO user=new UserDTO();
            shop.setShopId(s.getShopId());
            shop.setShopName(s.getNameShop());
            shop.setLogo(s.getLogo());
            shop.setIsActive(s.getIsActive());
            user.setUserId(s.getUserShop().getId());
            user.setFullName(s.getUserShop().getFullName());
            shop.setUserDTO(user);

            return shop;
        }).collect(Collectors.toList());

        return shops;
    }

}
