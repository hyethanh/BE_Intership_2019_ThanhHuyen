package com.example.book.reposities;

import com.example.book.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleReposity extends JpaRepository<Role,Integer> {

    Role findByName(String name);

}
