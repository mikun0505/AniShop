package com.example.java.anishop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java.anishop.repository.entity.Roles;


public interface RoleRepository extends JpaRepository<Roles, Long>{
   Optional<Roles> findByName(String name);   // phải có Optionsl mới dùng dc orElseThrow
}
