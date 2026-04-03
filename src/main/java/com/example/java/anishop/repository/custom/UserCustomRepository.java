// package com.example.java.anishop.repository.custom;

// import java.util.List;

// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// import com.example.java.anishop.model.UserDTO;

// @Repository
// public interface UserCustomRepository {
//     @Query("SELECT u.userId,u.avatar,u.isActive,u.fullName FROM Users u WHERE LOWER(u.fullName) LIKE 
//      LOWER(CONCAT('%', :keyword, '%'))"
//     )
//     List<UserDTO> searchUser(@Param("keyword") String keyword);
// }
