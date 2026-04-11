package com.example.java.anishop.model.reponse;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    private Long reviewId;
    private UserDTO reviewUserDTO;
    private AnimeDTO reviewAnimeDTO;
    private String context;
    private Integer rating;
    private LocalDateTime createdAt;
}
