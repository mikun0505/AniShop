package com.example.java.anishop.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.java.anishop.exception.AppException;


@Component
public class SecurityUtils {

    public static String getCurrentUserEmail(){
        Authentication authen=SecurityContextHolder.getContext().getAuthentication();
        if(authen==null || !authen.isAuthenticated()){
            throw new AppException("Chưa Đăng nhập", 401);
        }
        return authen.getName(); // lấy email từ JWT
    }
}
