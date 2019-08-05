package com.example.book.reposities;

import com.example.book.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReposity extends JpaRepository<Book,Integer> {
}
