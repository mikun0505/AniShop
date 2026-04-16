package com.example.java.anishop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.entity.Carts;


@Repository
public interface CartRepository extends JpaRepository<Carts, Long>{
    void deleteByUserCarts_Id(Long id);
    // tìm theo userid ch dc xóa 
    Optional<Carts> findByUserCartsIdAndDeletedFalse(Long id);
   
}
