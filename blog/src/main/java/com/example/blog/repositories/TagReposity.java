package com.example.blog.repositories;

import com.example.blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagReposity extends JpaRepository<Tag,Integer> {

   Optional<Tag> findByName(String name);
}
