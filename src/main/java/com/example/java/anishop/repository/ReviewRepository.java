package com.example.java.anishop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.entity.Reviews;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews,Long>{
    // Tìm danh sách bình luận của 1 anime
    List<Reviews> findByReviewAnimeAnilistId(Long anilistId);
}
