package com.example.java.anishop.repository.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @Column(name="anilist_id") // max từ API
    private Long anilistId;

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

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name="update_at")
    private LocalDateTime updateAt;
    
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
