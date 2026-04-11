package com.example.java.anishop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.entity.Caregories;


@Repository
public interface CaregoriRepository extends JpaRepository<Caregories, Long> {
    // kiểm tra danh mục đã tồn tại chưa
    boolean existsByCaregoriName(String caregoriName);
    // tiếm kiếm theo name
    
    Optional<Caregories> findByCaregoriName(String caregoriName);

}
