package com.example.java.anishop.testCase;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.java.anishop.exception.AppException;
import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.reponse.UserDTO;
import com.example.java.anishop.model.request.LoginRequest;
import com.example.java.anishop.model.request.RegisterRequest;
import com.example.java.anishop.repository.RoleRepository;
import com.example.java.anishop.repository.UserRepository;
import com.example.java.anishop.repository.entity.Roles;
import com.example.java.anishop.repository.entity.Users;
import com.example.java.anishop.service.impl.JwtService;
import com.example.java.anishop.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private AuthenticationManager authen;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;


    private RegisterRequest registerRequest;
    private LoginRequest login;
    private Users mockUser;

    @BeforeEach
    void setUp(){
        mockUser =new Users();
        mockUser.setId(1L);
        mockUser.setEmail("cuoc@gmail.com");
        mockUser.setPassword("encodedPassword");
        mockUser.setFullName("Miku");
        mockUser.setCreatedAt(LocalDateTime.now());
        mockUser.setIsActive(true);
        mockUser.setDeleted(false);

        registerRequest=new RegisterRequest();
        registerRequest.setEmail("cuoc@gmail.com");
        registerRequest.setName("Miku");
        registerRequest.setPassword("cuoc123");

        login=new LoginRequest();
        login.setEmail("cuoc@gmail.com");
        login.setPassword("cuoc123");
        
    } 

    @Test
    void testSearchId_ShoudReturnUserDTO(){
        // Thiết lập DB giả 
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // Gọi  hàm thật
        UserDTO result=userService.searchId(1L);

        //Kiểm tra với kq khi đã giả lâp
        assertEquals("Miku",result.getFullName());
        
        // verify đảm bào chạy đúng 1 lần
        verify(userRepository,times(1)).findById(1L);
    }
    

    @Test
    void testSearchId_ShoudThrowIfNotFound(){
        // giả dữ liệu
        when(userRepository.findById(11L)).thenReturn(Optional.empty());

        assertThrows(AppException.class,() -> { 
            userService.searchId(11L);
        });

        verify(userRepository,times(1)).findById(11L);

    }

    @Test
    void testSearchUser_ShoudReturnList(){
        // giả DB
        when(userRepository.searchUser("Miku")).thenReturn(List.of(mockUser));
        // Gọi DB thật
        List<UserDTO> userDTOs=userService.searchUser("Miku");

        // kiểm tra
        assertEquals("Miku", userDTOs.get(0).getFullName());

        // đảm bảo chạy đúng 1 lần

        verify(userRepository,times(1)).searchUser("Miku");
    }

    @Nested
    @DisplayName("register()")
    class RegisterTest{

        @Test
        @DisplayName("Đăng kí thành công - email chưa tồn tại")
        void testRegister_ShouldReturnApiResponse(){
            // tạo Role sẵn
            Roles role=new Roles();
            role.setName("ROLE_USER");

            // giả db
            when(userRepository.existsByEmail("cuoc@gmail.com")).thenReturn(false);
            when(passwordEncoder.encode("cuoc123")).thenReturn("encodedPassword");
            when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
            when(userRepository.save(any(Users.class))).thenReturn(mockUser);  // cách kiểm tra lưu trong test

            // tạo ApiResponse phương thức trả ra để gọi hàm thật so sánh
            ApiResponse<?> reponse=userService.register(registerRequest);

            // kieemr tra

            assertNotNull(reponse);
            assertEquals(201,reponse.getStatus());
            assertEquals("Đăng kí thành công",reponse.getMessage());

            // tạo DTO của user để nhận giá trị trả ra để kiểm tra
            UserDTO dto=(UserDTO)reponse.getData();
            assertEquals(true, dto.getIsActive());
            assertEquals("Miku", dto.getFullName());

            // kiểm tra đảm bảo chỉ chạy đúng một lần và kh sai
            verify(userRepository,times(1)).existsByEmail("cuoc@gmail.com");
            verify(passwordEncoder,times(1)).encode("cuoc123");
            verify(userRepository,times(1)).save(any(Users.class));


        }

        @Test
        @DisplayName("Đăng kí thất bại email đã tồn tại")
        void testRegister_ShouldThrowIfNotFound(){
            // giả DB
            when(userRepository.existsByEmail("cuoc@gmail.com")).thenReturn(true);
            
            // gọi hàm thật
            AppException ex=assertThrows(AppException.class,()-> {
                userService.register(registerRequest);

            });

            assertEquals("Email đã tồn tại",ex.getMessage());
            assertEquals(404, ex.getStatusCode());

            // đảm bào hàm kh dc gọi vì gmail trùng
            verify(userRepository,never()).save(any());
        }

        //test lưu role vào nếu nhứ ch có role
        @Test
        @DisplayName("Lưu role tự động khi role ch tồn tại")
        void testRegister_ShouldReturnApiResponseRole(){
            // tạo role auto
            Roles roles=new Roles();
            roles.setName("ROLE_USER");
            // gọi DB giả
            when(userRepository.existsByEmail("cuoc@gmail.com")).thenReturn(false);
            when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty()); // không cs role
            // lưu role
            when(roleRepository.save(any(Roles.class))).thenReturn(roles);

            when(passwordEncoder.encode("cuoc123")).thenReturn("encodedPassword");
            // lưu user
            when(userRepository.save(any(Users.class))).thenReturn(mockUser);

            ApiResponse<?> reponse=userService.register(registerRequest);

            assertNotNull(reponse);

            assertEquals(201,reponse.getStatus());

            verify(roleRepository).save(any(Roles.class));
        }
    }
}
