package com.example.java.anishop.service;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.request.ReviewRequest;


@Service
public interface ReviewService {
    // Láy danh sách bình luận của 1 anime ai cx có thể xem dc
    ApiResponse<?> getReview(Long anilistId);
    //Thêm bình luận phải đăng nhập mới thêm đc
    ApiResponse<?> addReview(ReviewRequest request);

    // sửa bình luận phải là chủ của bình luận thì mới sửa đc
    ApiResponse<?> updateReview(ReviewRequest request);

    // xóa bình luận phải là chủ của bình luận

    ApiResponse<?> deleteReview(Long reviewId);

}
