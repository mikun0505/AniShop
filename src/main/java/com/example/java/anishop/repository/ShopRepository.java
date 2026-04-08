package com.example.java.anishop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.entity.Shops;

@Repository
public interface ShopRepository extends JpaRepository<Shops, Long>{
    @Query("SELECT s FROM Shops s WHERE LOWER(s.nameShop) LIKE LOWER(CONCAT('%', :nameShop, '%'))")  // JPQL query bằng tên field Java thay vì tên cột SQL
    @EntityGraph(attributePaths={"userShop"})       // tạo EAGER cần thiết để lấy thẳng fiels trong userShop fetch luôn relation trong cùng 1 query
                                                    // tránh Lazy Loading bị null
    List<Shops> findByNameShop(@Param("nameShop") String nameShop);
}
