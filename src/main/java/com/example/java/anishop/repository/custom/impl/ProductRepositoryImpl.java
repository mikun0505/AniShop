package com.example.java.anishop.repository.custom.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.java.anishop.builder.ProductSearchBuilder;
import com.example.java.anishop.repository.custom.ProductCustomRepository;
import com.example.java.anishop.repository.entity.Products;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;


@Repository
public class ProductRepositoryImpl implements ProductCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;

    public void queryNormal(ProductSearchBuilder productSearchBuilder,StringBuilder where,Map<String,Object> params){
            // try{
            //     Field[] fields=productSearchBuilder.getClass().getDeclaredFields();  // lấy tất cả cacs field trong ProductSearchBuilder
            //     for(Field it:fields){
            //         it.setAccessible(true); // cho phép đọc field dù là private
            //         String fieldName=it.getName();
            //         if(!fieldName.equals("productId")){
            //             Object value=it.get(productSearchBuilder);
            //             if(value!=null){
            //                 if(it.getType().getName().equals("java.lang.Long")){
            //                     where.append(" AND p.").append(fieldName).append(" = :").append(fieldName);
            //                     params.put(fieldName, value);
            //                 }else if(it.getType().getName().equals("java.lang.Double")){
            //                     where.append(" AND p.").append(fieldName).append(" = :").append(fieldName);
            //                     params.put(fieldName, value);
            //                 }
            //                 else{
            //                     where.append(" AND p.").append(fieldName).append(" LIKE :").append(fieldName);
            //                     params.put(fieldName, "%"+value+"%");
            //                 }
            //             }
            //         }
            //     }
            // }catch(Exception e){
            //     e.printStackTrace();
            // }
            
                try {
                    Field[] fields = productSearchBuilder.getClass().getDeclaredFields();
                    for (Field it : fields) {
                        it.setAccessible(true);
                        String fieldName = it.getName();
                        Object value = it.get(productSearchBuilder);
            
                        if (value == null || fieldName.equals("productId")) continue;
            
                        if (fieldName.equals("shopId")) {
                            // shopId không có trực tiếp trong entity, phải đi qua relation
                            where.append(" AND p.shopProduct.shopId = :shopId");
                            params.put("shopId", value);
            
                        } else if (fieldName.equals("categoryId")) {
                            // categoryId không có trực tiếp trong entity, phải đi qua relation
                            where.append(" AND p.caregori.caregoryId = :categoryId");
                            params.put("categoryId", value);
            
                        } else if (fieldName.equals("priceMin")) {
                            where.append(" AND p.price >= :priceMin");
                            params.put("priceMin", value);
            
                        } else if (fieldName.equals("priceMax")) {
                            where.append(" AND p.price <= :priceMax");
                            params.put("priceMax", value);
            
                        } else if (it.getType() == Long.class || it.getType() == Double.class || it.getType() == Boolean.class) {
                            where.append(" AND p.").append(fieldName).append(" = :").append(fieldName);
                            params.put(fieldName, value);
            
                        } else {
                            // String -> dùng LIKE
                            where.append(" AND p.").append(fieldName).append(" LIKE :").append(fieldName);
                            params.put(fieldName, "%" + value + "%");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
     }
    

    // public void join(ProductSearchBuilder productSearchBuilder,StringBuilder sql){
    //     if(productSearchBuilder.getProductId()!=null){
    //         sql.append(" LEFT JOIN shops s ON s.shopId=p.shopId ");
    //         sql.append(" LEFT JOIN categories c ON c.categoryId=p.categoryId ");
    //     }

    // }
    public void queryWhere(ProductSearchBuilder productSearchBuilder,StringBuilder where,Map<String,Object> params){
            if(productSearchBuilder.getProductId()!=null){
                where.append(" AND p.productId=:productId");  // nếu đưa thẳng có nghuy cơ bị nguy hiểm
                params.put("productId",productSearchBuilder.getProductId());  // dặt vào map cho an toàn 
            }
    }

    @Override
    public List<Products> findAllProducts(ProductSearchBuilder productSearchBuilder) {
        StringBuilder sql=new StringBuilder("SELECT p FROM Products p ");

        StringBuilder where=new StringBuilder(" Where 1=1" );
        Map<String,Object> m=new HashMap<>();
        // join(productSearchBuilder, sql);
        System.out.println(sql.toString());
        queryWhere(productSearchBuilder, where, m);
        queryNormal(productSearchBuilder, where, m);
        sql.append(where);
        sql.append(" AND p.deleted=false ");
        Query query=entityManager.createQuery(sql.toString(),Products.class);  // createQuery tạo với JPQL con createNativeQuery là SQL thuần

        m.forEach(query::setParameter);
        
        return query.getResultList();


    }

}
