package com.example.java.anishop.repository.entity;
 
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor  // sinh ra: public ApiResponse() {}
@AllArgsConstructor 
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

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="update_at")
    private LocalDateTime updateAt;
    @Column(name="deleted")
    private Boolean deleted=false;

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

    @OneToOne(mappedBy="userCarts",cascade=CascadeType.ALL,fetch=FetchType.LAZY) //Khi thao tác với entity cha → entity con tự động làm theo
    private Carts userCart;

    @OneToMany(mappedBy="userOrder",fetch=FetchType.LAZY)
    private Set<Orders> orderUser=new HashSet<>();

    @ManyToMany(mappedBy="animeUser")
    private Set<Animes> userAnime=new HashSet<>();

    @OneToMany(mappedBy="reviewUser",fetch=FetchType.LAZY)
    private Set<Reviews> userReview=new HashSet<>();
    
    
}