package com.example.blog.repositories;

import com.example.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReposity extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
