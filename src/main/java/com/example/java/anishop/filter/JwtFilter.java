package com.example.java.anishop.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.java.anishop.service.impl.JwtService;
import com.example.java.anishop.service.impl.MyUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
         HttpServletResponse response,
          FilterChain filterChain) throws ServletException, IOException {
               // BƯỚC 1: Lấy token từ Header Authorization
            String authHeader=request.getHeader("Authorization");

            String token=null;
            String userName=null;

            if(authHeader !=null && authHeader.startsWith("Bearer ")){
                token=authHeader.substring(7);
                try{
                    userName=jwtService.extractUserName(token);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails=userDetailService.loadUserByUsername(userName);

                if(jwtService.valiDateToken(token, userDetails)){
                    UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );

                SecurityContextHolder
                .getContext()
                .setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request,response);
    }

    
}
