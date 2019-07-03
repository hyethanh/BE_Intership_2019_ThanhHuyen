package com.example.blog.repositories;

import com.example.blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Integer> {

    Tag findByName(String name);

}
