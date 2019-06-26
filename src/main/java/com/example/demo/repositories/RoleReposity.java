package com.example.demo.repositories;

import com.example.demo.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleReposity extends JpaRepository<Role,Integer> {

    Role findByName(String name);
}
