package com.example.java.anishop.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.anishop.converter.MapperConverter;
import com.example.java.anishop.exception.AppException;
import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.reponse.ReviewDTO;
import com.example.java.anishop.model.request.ReviewRequest;
import com.example.java.anishop.repository.AnimeRepository;
import com.example.java.anishop.repository.ReviewRepository;
import com.example.java.anishop.repository.UserRepository;
import com.example.java.anishop.repository.entity.Animes;
import com.example.java.anishop.repository.entity.Reviews;
import com.example.java.anishop.repository.entity.Users;
import com.example.java.anishop.service.ReviewService;
import com.example.java.anishop.util.SecurityUtils;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private MapperConverter mapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse<?> getReview(Long anilistId) {
        List<Reviews> review=reviewRepository.findByReviewAnimeAnilistId(anilistId);
        
        List<ReviewDTO> dto=review.stream()
                .map(item->mapper.setReviewDTO(item))
                .collect(Collectors.toList());

        return ApiResponse.<List<ReviewDTO>>builder()
                .status(200)
                .message("Tìm thấy " + dto.size() + " bình luận ")
                .data(dto)
                .build();
    }

    @Override
    public ApiResponse<?> addReview(ReviewRequest request) {
        String email=securityUtils.getCurrentUserEmail();

        Users user=userRepository.findByEmail(email)
            .orElseThrow(()-> new AppException("Không tìm thấy User", 404));

        // anime phải dc lưu về DB trước miws có thể bình luận

        Animes anime=animeRepository.findByAnilistId(request.getAnilistId())
                .orElseThrow(()-> new AppException("Không tìm thấy ch đc lưu vào hệ thống  ", 404));

        Reviews reviews=new Reviews();
        reviews.setContent(request.getContext());
        reviews.setCreatedAt(LocalDateTime.now());
        reviews.setRating(request.getRating());
        reviews.setReviewAnime(anime);
        reviewRepository.save(reviews);

        ReviewDTO dto=mapper.setReviewDTO(reviews);
        return ApiResponse.<ReviewDTO>builder()
                .status(200)
                .message("Đã thêm thành công")
                .data(dto)
                .build();
    }

    @Override
    public ApiResponse<?> updateReview(ReviewRequest request) {
        String email=securityUtils.getCurrentUserEmail();

        Users user=userRepository.findByEmail(email)
                .orElseThrow(()-> new AppException("Không tìm thấy user",404));

        Reviews reviews=reviewRepository.findById(request.getReviewId())
                .orElseThrow(()-> new AppException("Không tìm thấy bình luận", 404));

        
        if(!reviews.getReviewUser().getId().equals(user.getId())){
            throw new AppException("Không có quyền sủa bình luận",403);
        }

        reviews.setUpdateAt(LocalDateTime.now());
        reviews.setContent(request.getContext());
        reviewRepository.save(reviews);

        ReviewDTO dto=mapper.setReviewDTO(reviews);
        return ApiResponse.<ReviewDTO>builder()
                    .status(200)
                    .message("Đã sửa thành công")
                    .data(dto)
                    .build();
    }

    @Override
    public ApiResponse<?> deleteReview(Long reviewId) {
        String email=securityUtils.getCurrentUserEmail();
        Users user=userRepository.findByEmail(email)
                    .orElseThrow(()-> new AppException("Không tìm thấy user",404));

        Reviews reviews=reviewRepository.findById(reviewId)
                    .orElseThrow(()-> new AppException("Không tìm thấy bình luận",404));

        if(!reviews.getReviewUser().getId().equals(user.getId())){
             throw new AppException("Không có quyền truy cập ",403);
        }

        reviews.setDeleted(true);
        reviewRepository.save(reviews);

        return ApiResponse.<String>builder()
                    .status(200)
                    .message("Đã xóa thành công")
                    .data(null)
                    .build();
    }

}
