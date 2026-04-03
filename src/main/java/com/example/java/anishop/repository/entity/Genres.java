package com.example.java.anishop.repository.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="genres")
public class Genres {  // Thể loại
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long genreId;

    @Column(name="genre_name")
    private String genreName;

    @ManyToMany(mappedBy="animeGenre")
    private Set<Animes> genreAnimes=new HashSet<>();

}
