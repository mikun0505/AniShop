package com.example.java.anishop.repository.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name="reviews")
public class Reviews {  //Bình luận

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long reviewId;

    // private Long userId;

    // private Long animeId;

    @Column(name="content")   // Nội dung bình luận
    private String content;  

    @Column(name="rating")
    private Integer rating;
    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name="update_at")
    private LocalDateTime updateAt;
    
    @Column(name="deleted")
    private Boolean deleted=false;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Users reviewUser;

    @ManyToOne
    @JoinColumn(name="anime_id")
    private Animes reviewAnime;

}
