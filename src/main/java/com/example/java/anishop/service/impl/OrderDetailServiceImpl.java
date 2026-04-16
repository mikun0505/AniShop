package com.example.java.anishop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.anishop.converter.MapperConverter;
import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.reponse.OrderDetailDTO;
import com.example.java.anishop.repository.OrderDetailRepository;
import com.example.java.anishop.repository.entity.OrderDetails;
import com.example.java.anishop.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private MapperConverter mapperConverter;
    @Override
    public ApiResponse<?> getOrderDetails(Long orderId) {
        List<OrderDetails> order=orderDetailRepository.findByOrderOrderIdAndDeletedFalse(orderId);

        List<OrderDetailDTO> dto = order.stream().map(item-> mapperConverter.setOrderDTO(item))
                .collect(Collectors.toList());

        return ApiResponse.<List<OrderDetailDTO>>builder()
                    .status(200)
                    .message("Đã tìm thấy")
                    .data(dto)
                    .build();
    }

}
