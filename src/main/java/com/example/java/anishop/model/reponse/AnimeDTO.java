package com.example.java.anishop.model.reponse;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimeDTO {
    private Long animeId;
    private String animeName;
    private Long anilistId;
    private String coverImg;
    private Float score;
    private Long episodes;
    private Long viewCount;
    private String status;
    private String synopsis;   // nội dung tóm tắt
    private List<GenreDTO> animeGenres;
}
