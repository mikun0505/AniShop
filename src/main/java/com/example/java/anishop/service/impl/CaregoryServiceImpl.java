package com.example.java.anishop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.anishop.converter.MapperConverter;
import com.example.java.anishop.exception.AppException;
import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.reponse.CaregoryDTO;
import com.example.java.anishop.model.request.CaregoryRequest;
import com.example.java.anishop.repository.CaregoriRepository;
import com.example.java.anishop.repository.entity.Caregories;
import com.example.java.anishop.service.CaregoryService;

@Service
public class CaregoryServiceImpl implements CaregoryService{

    @Autowired
    private CaregoriRepository caregoriRepository;
    @Autowired
    private MapperConverter caregoryConveter;
    @Override
    public ApiResponse<?> createdCaregori(CaregoryRequest request) {
        
        if(caregoriRepository.findByCaregoryName(request.getCaregoryName()).isPresent()){  // isPresent tồn tại trong thư viện Optional dung để kiểm ta xem thử đã tồn tại ch
            throw new RuntimeException("Caregory đã tồn tại");
        }
        
        Caregories caregories=new Caregories();
        caregories.setCaregoryName(request.getCaregoryName());
        caregoriRepository.save(caregories);
        CaregoryDTO dto=caregoryConveter.setCaregoryDTO(caregories);
        return ApiResponse.<CaregoryDTO>builder()
                .status(201)
                .message("Đã thêm thành công")
                .data(dto)
                .build();
    }

    @Override
    public ApiResponse<?> updateCaregory(CaregoryRequest request) {
        Caregories caregories=caregoriRepository.findById(request.getCaregoryId())
                .orElseThrow(()-> new AppException("Danh mục không tồn tại", 404));

        caregories.setCaregoryName(request.getCaregoryName());
        CaregoryDTO dto=caregoryConveter.setCaregoryDTO(caregories);
        return ApiResponse.<CaregoryDTO>builder()
                    .status(200)
                    .message("Đã update thành công")
                    .data(dto)
                    .build();
    }

    @Override
    public ApiResponse<?> deletedCaregory(Long id) {
        Caregories caregories=caregoriRepository.findById(id)
        .orElseThrow(()-> new AppException("Danh mục không tồn tại", 404));

        caregoriRepository.delete(caregories);

        return ApiResponse.<String>builder()
                    .status(204)  // No Content không tả về data , data=null
                    .message("Đã xóa thành công")
                    .data(null)
                    .build();
    }

    @Override
    public ApiResponse<?> findCaregoryId(Long id) {
        Caregories caregories=caregoriRepository.findById(id)
                    .orElseThrow(()-> new AppException("Không tìm thấy danh mục", 404));

        CaregoryDTO dto=caregoryConveter.setCaregoryDTO(caregories);
        return ApiResponse.<CaregoryDTO>builder()
                    .status(200)
                    .message("Đã tìm thấy")
                    .data(dto)
                    .build();
    }

    @Override
    public ApiResponse<?> findAllCaregory() {
        List<Caregories> caregories=caregoriRepository.findAll();

        List<CaregoryDTO> dto=new ArrayList<>();
        for(Caregories it:caregories){
            CaregoryDTO caregoryDTO=caregoryConveter.setCaregoryDTO(it);
            dto.add(caregoryDTO);
            
        }
        return ApiResponse.<List<CaregoryDTO>>builder()
                    .status(200)
                    .message("Tất cả các danh mục")
                    .data(dto)
                    .build();
    }

}   
