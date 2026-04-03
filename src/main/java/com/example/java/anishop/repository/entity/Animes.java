package com.example.java.anishop.repository.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="animes")
public class Animes {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long animeId;

    @Column(name="mal_id") // max từ API
    private Long maiId;

    @Column(name="title")
    private String title;

    @Column(name="synopsis")
    private String synopsis;

    @Column(name="cover_image")
    private String coverImage;

    @Column(name="score")
    private Float score;

    @Column(name="episodes")
    private Integer episodes;

    @Column(name="view_count")
    private Long viewCount;
    
    @Column(name="status")
    private Boolean status;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
        name="anime_genres",
        joinColumns=@JoinColumn(name="anime_id"),
        inverseJoinColumns =@JoinColumn(name="genre_id")
    )
    private Set<Genres> animeGenre=new HashSet<>();
    

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
        name="favorites",
        joinColumns=@JoinColumn(name="anime_id"),
        inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private Set<Users> animeUser=new HashSet<>();


    @OneToMany(mappedBy="reviewAnime",fetch=FetchType.LAZY)
    private Set<Reviews> animeReview=new HashSet<>();
}
