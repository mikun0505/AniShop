package com.example.java.anishop.repository.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="caregories")
public class Caregories {   // Danh mục

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long caregorieId;
 
    @Column(name="caregori_name")
    private String caregoriName;

    @OneToMany(mappedBy="caregori",fetch=FetchType.LAZY)
    private Set<Products> cate=new HashSet<>(); 

}
