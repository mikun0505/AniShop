package com.example.java.anishop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.request.ReviewRequest;
import com.example.java.anishop.service.ReviewService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;




@RestController

public class ReviewApi {

    @Autowired
    private ReviewService reviewService;
    
    // lấy tất cả bình luận của bộ anime đó
    @GetMapping("/api/reviews/{anilistId}")
    public ResponseEntity<ApiResponse<?>> getReview(@PathVariable @Min(1) Long anilistId) {
        return ResponseEntity.ok(reviewService.getReview(anilistId));
    }
    
    @PostMapping("/api/reviews")
    public ResponseEntity<ApiResponse<?>> addReview(@Valid @RequestBody ReviewRequest request ) {
        //TODO: process POST request
        
        return ResponseEntity.ok(reviewService.addReview(request));
    }
    
    @PutMapping("/api/reviews")
    public ResponseEntity<ApiResponse<?>> updateReview(@Valid @RequestBody ReviewRequest request) {
        //TODO: process PUT request
        
        return ResponseEntity.ok(reviewService.updateReview(request));
    }


    @DeleteMapping("/api/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<?>> deleteReview(@PathVariable @Min(1) Long reviewId){
        return ResponseEntity.ok(reviewService.deleteReview(reviewId));
    }
    
}
