package com.example.java.anishop.model.reponse;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO implements Serializable{
    private static final long cacheUserId=1L;
    private Long userId;
    private String fullName;
    private String avatar;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
