package com.example.java.anishop.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimeDTO {
    private Long animeId;
    private String animeName;
    private Long malId;
    private String coverImg;
    private Float score;
    private Long episodes;
    private Long viewCount;
    private String status;
    private String synopsis;   // nội dung tóm tắt
    private List<GenreDTO> animeGenres;
}
