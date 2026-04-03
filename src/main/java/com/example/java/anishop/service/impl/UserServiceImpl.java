package com.example.java.anishop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.anishop.model.UserDTO;
import com.example.java.anishop.repository.UserRepository;
import com.example.java.anishop.repository.entity.Users;
import com.example.java.anishop.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public List<UserDTO> searcUser(String keyword) {
        List<UserDTO> user=userRepository.searchUser(keyword)
        .stream().map((var us)-> {   // us lấy từ entity
            UserDTO dto=new UserDTO();
            dto.setUserId(us.getId());
            dto.setFullName(us.getFullName());
            dto.setAvarta(us.getAvatar());
            dto.setCreatedAt(us.getCreateAt());

            return dto;
        }).collect(Collectors.toList());

        return user;

    }

    @Override
    public UserDTO searchId(Long id) {
        Users searchId=userRepository.findById(id).get();
        UserDTO result=new UserDTO();
        result.setUserId(searchId.getId());
        result.setAvarta(searchId.getAvatar());
        result.setFullName(searchId.getFullName());
        result.setIsactive(searchId.getIsActive());

        return result;
    }

}
