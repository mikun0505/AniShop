package com.example.java.anishop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.entity.Genres;


@Repository
public interface GenreRepository extends JpaRepository<Genres, Long>{

    // tìm theo tên thể loại
    Optional<Genres> findByGenreName(String genreName);
}
