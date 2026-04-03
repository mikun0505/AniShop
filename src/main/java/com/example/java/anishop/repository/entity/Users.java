package com.example.java.anishop.repository.entity;
 
import java.time.LocalDateTime;
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
@Table(name="users")
public class Users{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="full_name")
    private String fullName;

    @Column(name="avatar")
    private String avatar;

    @Column(name="is_active")
    private Boolean isActive;

    @Column(name="create_at")
    private LocalDateTime createAt;

    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(
        name="user_roles",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Roles> roles=new HashSet<>();

    @OneToMany(mappedBy="user" ,fetch=FetchType.LAZY)
    private Set<RefreshToken> refreshTokens=new HashSet<>();

    @OneToMany(mappedBy="userShop",fetch=FetchType.LAZY)
    private Set<Shops> shop=new HashSet<>();

    @OneToMany(mappedBy="userCarts",fetch=FetchType.LAZY)
    private Set<Carts> carts=new HashSet<>();

    @OneToMany(mappedBy="userOrder",fetch=FetchType.LAZY)
    private Set<Orders> orderUser=new HashSet<>();

    @ManyToMany(mappedBy="animeUser")
    private Set<Animes> userAnime=new HashSet<>();

    @OneToMany(mappedBy="reviewUser",fetch=FetchType.LAZY)
    private Set<Reviews> userReview=new HashSet<>();
    
    
}