package com.example.java.anishop.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.java.anishop.repository.UserRepository;
import com.example.java.anishop.repository.entity.Users;
import com.example.java.anishop.repository.security.UserPrincipal;

@Service
public class MyUserDetailService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=userRepository.findByEmail(username);
        if(user==null){
            System.out.println("Không đăng nhập được !");
            throw new UsernameNotFoundException("Users Not Found");

        }
        return new UserPrincipal(user);
    }

    
}
