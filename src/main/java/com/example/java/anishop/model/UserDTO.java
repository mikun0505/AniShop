package com.example.java.anishop.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long userId;
    private String fullName;
    private String avarta;
    private Boolean Isactive;
    private LocalDateTime createdAt;
}
