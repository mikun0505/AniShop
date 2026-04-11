package com.example.java.anishop.model.reponse;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long userId;
    private String fullName;
    private String avatar;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
