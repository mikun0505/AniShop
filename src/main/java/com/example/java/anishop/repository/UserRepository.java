package com.example.java.anishop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
      @Query(value="""
        SELECT u.user_id,u.avatar,u.is_active,u.full_name FROM users u WHERE LOWER(u.full_name) LIKE 
     LOWER(CONCAT('%', :keyword, '%')) AND u.is_active=true
             
             """, nativeQuery=true
    )
    List<Users> searchUser(@Param("keyword") String keyword);

    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);

    
}
