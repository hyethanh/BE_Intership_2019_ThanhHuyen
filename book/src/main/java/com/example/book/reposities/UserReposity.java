package com.example.book.reposities;

import com.example.book.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReposity extends JpaRepository<User,Integer> {

    User findByEmail(String email);
}
