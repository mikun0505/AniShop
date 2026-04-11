package com.example.java.anishop.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaregoryRequest {
    private Long caregoryId;

    @NotNull(message="Danh mục không được rỗng")
    @Size(min=2,max=100,message="Tên từ 2 - 100 kí tự")
    private String caregoryName;
}
