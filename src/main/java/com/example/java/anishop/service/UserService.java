package com.example.java.anishop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.UserDTO;


@Service
public interface UserService {
    List<UserDTO> searcUser(String keyword);
    UserDTO searchId(Long id);
}
