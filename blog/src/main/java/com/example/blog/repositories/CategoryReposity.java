package com.example.blog.repositories;

import com.example.blog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Category findByName(String name);
}
