package com.example.demo.controllers;

import com.example.demo.exceptions.NotFoundException;
import com.example.demo.models.Book;
import com.example.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")

public class BookController {

 /*   @GetMapping("/{id}")
    Book get(@PathVariable int id){
        Book book = new Book();
        book.setId(id);
        book.setName("Harry Porter");
        book.setYear(2001);

        return book;}



    @GetMapping
    Book[] get(){
        Book book1 =new Book();
        book1.setId(1);
        book1.setName("Harry Porter");
        book1.setYear(2001);

        Book  book2 = new Book();
        book2.setId(2);
        book2.setName("Life of Pie");
        book2.setYear(2002);

        return new Book[]{book1,book2};
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id){
        System.out.println("Delete book id: "+id);
    }

    @PostMapping()
    void post(@RequestBody Book book){
        System.out.println("Create book "+book);
    }

    @PutMapping()
    void put(@RequestBody Book book){
        System.out.println("Update book "+book);
    }*/


    @Autowired
    private BookRepository bookRepository;

    /*@GetMapping("/{id}")
    Optional<Book> get(@PathVariable int id) {
        return bookRepository.findById(id);
    }*/

    @GetMapping("/{id}")
    Book get(@PathVariable int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            return optionalBook.get();
        }
        throw new NotFoundException(String.format("Book id %d is not found", id));
    }

    @GetMapping
    Iterable<Book> get() {
        return bookRepository.findAll(Sort.by("id"));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {

        if (!bookRepository.existsById(id)) {
            throw new NotFoundException(String.format("Book id %d is not found", id));
        }
        bookRepository.deleteById(id);
    }

    @PostMapping()
        //post sach moi
    void post(@RequestBody @Validated Book book) throws Exception {
       book.setId(0);
       bookRepository.save(book);
    }

    @PutMapping
    void put(@RequestBody Book book) {
        if (!bookRepository.existsById(book.getId())) {
            throw new NotFoundException(String.format("Book id %d is not found", book.getId()));
        }
        bookRepository.save(book);
    }
}
