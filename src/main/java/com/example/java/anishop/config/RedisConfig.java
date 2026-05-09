package com.example.java.anishop.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
@Configuration
@EnableCaching // bật caching
public class RedisConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory){  // // @Bean → đăng ký với Spring, khi nào cần cacheManager thì dùng cái này
                                                                           // RedisConnectionFactory → Spring tự inject kết nối Redis vào
        RedisCacheConfiguration config = RedisCacheConfiguration
        .defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(10)); // cache 10 phút
        return RedisCacheManager
            .builder(factory)        // dùng kết nối Redis đã có
            .cacheDefaults(config)   // áp dụng config bên trên
            .build();                // tạo ra CacheManager
        }
}
