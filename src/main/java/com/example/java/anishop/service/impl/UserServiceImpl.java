package com.example.java.anishop.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.java.anishop.exception.AppException;
import com.example.java.anishop.model.ApiResponse;
import com.example.java.anishop.model.UserDTO;
import com.example.java.anishop.model.respose.LoginRequest;
import com.example.java.anishop.model.respose.RegisterRequest;
import com.example.java.anishop.repository.RoleRepository;
import com.example.java.anishop.repository.UserRepository;
import com.example.java.anishop.repository.entity.Carts;
import com.example.java.anishop.repository.entity.Roles;
import com.example.java.anishop.repository.entity.Users;
import com.example.java.anishop.service.UserService;

import jakarta.transaction.Transactional;


@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    AuthenticationManager authen;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Override
    public List<UserDTO> searcUser(String keyword) {
        List<UserDTO> user=userRepository.searchUser(keyword)
        .stream().map((var us)-> {   // us lấy từ entity
            UserDTO dto=new UserDTO();
            dto.setUserId(us.getId());
            dto.setFullName(us.getFullName());
            dto.setAvatar(us.getAvatar());
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
        result.setAvatar(searchId.getAvatar());
        result.setFullName(searchId.getFullName());
        result.setIsActive(searchId.getIsActive());

        return result;
    }
    
    
    @Transactional
    @Override
    public ApiResponse<?> register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException("Email đã tồn tại",400);

        }
        Roles role=roleRepo.findByName("ROLE_USER")
                    .orElseThrow(() -> new AppException("Role không tồn tại", 404));    

        Users users=new Users();
        users.setEmail(request.getEmail());
        users.setFullName(request.getName());
        users.setPassword(passwordEncoder.encode(request.getPassword()));
        users.setIsActive(true);
        users.setCreateAt(LocalDateTime.now());
        users.getRoles().add(role);
        Carts cart=new Carts();
        cart.setUserCarts(users);
        cart.setCreatedAt(LocalDateTime.now());
        users.setUserCart(cart);
        userRepository.save(users);
        

        UserDTO udto=new UserDTO();
        udto.setFullName(users.getFullName());
        udto.setAvatar(users.getAvatar());
        udto.setUserId(users.getId());
        udto.setCreatedAt(users.getCreateAt());
        udto.setIsActive(users.getIsActive());

        return ApiResponse.<UserDTO>builder()
                .status(200)
                .message("Đăng kí thành công")
                .data(udto)
                .build();
        
    }
    
    @Override
    public  ApiResponse<?> login(LoginRequest login){
        
        Authentication authentication=authen.authenticate(new UsernamePasswordAuthenticationToken
            (login.getEmail(),login.getPassword()));
            
            if(authentication.isAuthenticated()){
                Map<String,String> token=new HashMap<>();
                token.put("acessToken",jwtService.generateToken(login.getEmail()));
                token.put("refreshToken", jwtService.generefreshToken(login.getEmail()));
                return ApiResponse.<Map<String,String>>builder()
                .status(200)
                .message("Đăng nhập thành công")
                .data(token)
                .build();
        }
        throw new AppException("Sai email hoặc password", 401);
    }

    @Override
    public ApiResponse<?> deleteById(Long id) {
        Users user=userRepository.findById(id)
                .orElseThrow(() -> new AppException("User không tồn tại",404));

        userRepository.deleteById(id);
        return ApiResponse.<String>builder()
                .status(200)
                .message("Đã xóa thành công !")
                .data(null)
                .build();
    }


}
