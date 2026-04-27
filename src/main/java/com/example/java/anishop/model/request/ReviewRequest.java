package com.example.java.anishop.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private Long reviewId;

    @NotNull(message="Không được rỗng")
    @Min(value=1,message="anilistId phải >=1 ")
    private Long anilistId;
    
    @NotBlank(message="Không đc rỗng")
    private String context;
    
    // điểm đánh giá
    @NotNull(message = "Rating không được trống")
    @Min(value = 1, message = "Rating tối thiểu là 1")
    @Max(value = 10, message = "Rating tối đa là 10")
    private Integer rating;

}
