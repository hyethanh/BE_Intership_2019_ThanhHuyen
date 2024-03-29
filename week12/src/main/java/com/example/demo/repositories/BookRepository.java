package com.example.demo.repositories;

import com.example.demo.models.dao.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findOneByName(String name);
}
