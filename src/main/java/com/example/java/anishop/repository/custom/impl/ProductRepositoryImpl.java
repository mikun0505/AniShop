package com.example.java.anishop.repository.custom.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.example.java.anishop.builder.ProductSearchBuilder;
import com.example.java.anishop.repository.custom.ProductCustomRepository;
import com.example.java.anishop.repository.entity.Products;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Primary
@Repository
public class ProductRepositoryImpl implements ProductCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;

    public void queryNormal(ProductSearchBuilder productSearchBuilder,StringBuilder where,Map<String,Object> params){
            try{
                Field[] fields=productSearchBuilder.getClass().getDeclaredFields();  // lấy tất cả cacs field trong ProductSearchBuilder
                for(Field it:fields){
                    it.setAccessible(true); // cho phép đọc field dù là private
                    String fieldName=it.getName();
                    if(!fieldName.equals("productId")){
                        Object value=it.get(productSearchBuilder);
                        if(value!=null){
                            if(it.getType().getName().equals("java.lang.Long")){
                                where.append(" AND p.").append(fieldName).append(" = :").append(fieldName);
                                params.put(fieldName, value);
                            }else if(it.getType().getName().equals("java.lang.Double")){
                                where.append(" AND p.").append(fieldName).append(" = :").append(fieldName);
                                params.put(fieldName, value);
                            }
                            else{
                                where.append(" AND p.").append(fieldName).append(" LIKE :").append(fieldName);
                                params.put(fieldName, "%"+value+"%");
                            }
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
    }

    public void join(ProductSearchBuilder productSearchBuilder,StringBuilder sql){
        if(productSearchBuilder.getProductId()!=null){
            sql.append(" LEFT JOIN shops s ON s.shopId=p.shopId ");
            sql.append(" LEFT JOIN categoryId c ON c.categoryId=p.categoryId ");
        }

    }
    public void queryWhere(ProductSearchBuilder productSearchBuilder,StringBuilder where,Map<String,Object> params){
            if(productSearchBuilder.getProductId()!=null){
                where.append(" AND p.productId=:productId");  // nếu đưa thẳng có nghuy cơ bị nguy hiểm
                params.put("productId",productSearchBuilder.getProductId());  // dặt vào map cho an toàn 
            }
    }

    @Override
    public List<Products> findAll(ProductSearchBuilder productSearchBuilder) {
        StringBuilder sql=new StringBuilder("""
                  SELECT p.product_id, p.product_name, p.shop_id, 
                          p.category_id, p.price, p.status
                          FROM products p """); 

        StringBuilder where=new StringBuilder(" Where 1==1" );
        Map<String,Object> m=new HashMap<>();
        join(productSearchBuilder, sql);
        System.out.println(sql.toString());
        queryWhere(productSearchBuilder, where, m);
        queryWhere(productSearchBuilder, where, m);
        sql.append(where);

        Query query=entityManager.createNativeQuery(sql.toString(),Products.class);

        m.forEach(query::setParameter);
        
        return query.getResultList();


    }

}
