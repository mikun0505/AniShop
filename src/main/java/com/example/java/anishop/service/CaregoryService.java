package com.example.java.anishop.service;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.request.CaregoryRequest;


@Service
public interface CaregoryService {
    // tạo danh mục
    ApiResponse<?> createdCaregori(CaregoryRequest request);
    // update danh mục
    ApiResponse<?> updateCaregory(CaregoryRequest request);
    // delete danh mục
    ApiResponse<?> deletedCaregory(Long id);
    // tìm kiếm theo id
    ApiResponse<?> findCaregoryId(Long id);
    // tím tất cả danh mục
    ApiResponse<?> findAllCaregory();

}
