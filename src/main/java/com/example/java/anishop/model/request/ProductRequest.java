package com.example.java.anishop.model.request;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductRequest {

    private Long productId;

    @NotBlank(message="Tên không được null")
    @Size( min=2,max=100,message="Tên từ 2 kí tự đến 100")
    private String productName;
    
    @NotNull(message="Shop không được null")
    private Long shopId; 

    @NotNull(message="Danh mục kh được trống")
    private Long carigoruId;

    @NotNull(message="Giá kh được rổng")
    private Double price;

    @Size(max=500,message="Mô tả kh được quá 500 kí tự")
    private String description;

    @NotNull(message="số lượng kh được trống")
    @Min(value=0,message="Số lượng kh được âm")
    private Long stock;

    private Boolean status;

    @NotEmpty(message="Sản phẩm phải có ít nhất 1 ảnh")
    private List<String> imgUrl;

}
