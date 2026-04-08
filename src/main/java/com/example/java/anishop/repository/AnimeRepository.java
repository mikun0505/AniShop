package com.example.java.anishop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.entity.Animes;

@Repository
public interface AnimeRepository extends JpaRepository<Animes, Long>{
    List<Animes> findByTitleContainingIgnoreCase(String animeName);
}
