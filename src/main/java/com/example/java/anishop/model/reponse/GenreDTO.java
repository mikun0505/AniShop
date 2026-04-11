package com.example.java.anishop.model.reponse;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreDTO {
    private Long genreId;
    private String genrName;
    private List<AnimeDTO> animes;
}
