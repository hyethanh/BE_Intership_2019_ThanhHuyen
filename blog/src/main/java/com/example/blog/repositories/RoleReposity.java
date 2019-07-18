package com.example.blog.repositories;

import com.example.blog.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleReposity extends JpaRepository<Role,Integer> {
    Role findByName(String name);
}