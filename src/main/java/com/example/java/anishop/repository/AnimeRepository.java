package com.example.java.anishop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.entity.Animes;

@Repository
public interface AnimeRepository extends JpaRepository<Animes, Long>{
    // tìm theo tên 
    List<Animes> findByTitleContainingIgnoreCase(String animeName);

    // check xem anime đã sync bề DB ch trước khi gọi
    Optional<Animes> findByAnilistId(Long aniListId);
}
