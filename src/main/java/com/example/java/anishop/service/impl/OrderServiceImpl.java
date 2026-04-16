package com.example.java.anishop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.anishop.converter.MapperConverter;
import com.example.java.anishop.enums.OrderStatus;
import com.example.java.anishop.exception.AppException;
import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.reponse.OrderDTO;
import com.example.java.anishop.model.request.OrderRequest;
import com.example.java.anishop.repository.OrderRepository;
import com.example.java.anishop.repository.ShopRepository;
import com.example.java.anishop.repository.entity.Orders;
import com.example.java.anishop.repository.entity.Shops;
import com.example.java.anishop.service.OrderService;
import com.example.java.anishop.service.ShopService;
import com.example.java.anishop.util.SecurityUtils;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private MapperConverter mapperConverter;
    @Autowired
    private ShopService shopService;
    @Override
    public ApiResponse<?> getOrderByUserId(Long userId) {
        List<Orders> or=orderRepository.findByUserOrderIdAndDeletedFalse(userId);

        List<OrderDTO> dto=or.stream().map(it-> mapperConverter.setOrderDTO(it)).collect(Collectors.toList());

        return ApiResponse.<List<OrderDTO>>builder()
                    .status(200)
                    .message("Đã tìm thấy")
                    .data(dto)
                    .build();
            
    }

    @Override
    public ApiResponse<?> getOrderId(Long orderId) {
        Orders order=orderRepository.findById(orderId)
                    .orElseThrow(()-> new AppException("Không tìm thấy mã đơn hàng", 404));
        
        OrderDTO dto=mapperConverter.setOrderDTO(order);

        return ApiResponse.<OrderDTO>builder()
                    .status(200)
                    .message("Đã tìm thấy")
                    .data(dto)
                    .build();

    }

    @Transactional
    @Override
    public ApiResponse<?> updateOrderStatus(OrderRequest request) {
        String email=securityUtils.getCurrentUserEmail();

        Shops shop=shopRepository.findByShopIdAndDeletedFalse(request.getShopId())
                .orElseThrow(()-> new AppException("Không tìm thấy shop", 404));
        // kiểm ta có phải chủ shop kh
        shopService.validateShopOwner(shop, email);
        
        Orders order=orderRepository.findById(request.getOrderId())
                    .orElseThrow(()-> new AppException("Không tìm thấy mã đơn hàng ",404));
        try{
            order.setStatus(OrderStatus.valueOf(request.getStatus().toUpperCase()));
        }catch(Exception e){
            e.printStackTrace();
        }
        orderRepository.save(order);
        return ApiResponse.<String>builder()
                .status(200)
                .message("Đã cập nhật thành công")
                .data(request.getStatus())
                .build();
    }

    @Transactional
    @Override
    public ApiResponse<?> deletedOrder(Long orderId) {
        Orders order=orderRepository.findById(orderId)
                .orElseThrow(()-> new AppException("Không tìm thấy đơn hàng", 404));

        if(order.getStatus().equals("SHIPPING") || order.getStatus().equals(" DELIVERED")){
            throw new RuntimeException("Không thể hủy đơn hàng");
        }

        order.setStatus(OrderStatus.valueOf("CANCELLED"));
        orderRepository.save(order);
        return ApiResponse.<String>builder()
                .status(203)
                .message("Đã hủy thành công")
                .data(null)
                .build();
    }

}
