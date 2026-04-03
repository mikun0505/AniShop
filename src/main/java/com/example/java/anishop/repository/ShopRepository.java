package com.example.java.anishop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.repository.entity.Shops;

@Repository
public interface ShopRepository extends JpaRepository<Shops, Long>{
    @Query(value="""
            SELECT s.shop_id,s.name_shop,s.logo
            ,s.description,s.is_active,s.user_id FROM shops s WHERE LOWER(s.name_shop) LIKE LOWER(CONCAT('%', :nameShop , '%' ))

            """, nativeQuery=true)
    List<Shops> findByNameShop(@Param("nameShop") String nameShop);

}
