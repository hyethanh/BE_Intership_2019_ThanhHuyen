package com.example.demo.controllers;

import com.example.demo.converters.bases.Converter;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.models.dao.Book;
import com.example.demo.models.dto.BookDTO;
import com.example.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/books")

public class BookController {

    @Autowired
    private Converter<BookDTO,Book> bookDTOConverter;

    @Autowired
    private Converter<Book,BookDTO> bookDAOConverter;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/{id}")
    public BookDTO get(@PathVariable int id){
        Optional<Book> optionalBook = bookRepository.findById(id);

        if(optionalBook.isPresent()){
            return bookDAOConverter.convert(optionalBook.get());
        }

        throw new NotFoundException(String.format("Book id %d not found", id));
    }

    @GetMapping
    Iterable<BookDTO> get() {

        return bookDAOConverter.convert(bookRepository.findAll(Sort.by("id")));
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
    void post(@RequestBody @Validated BookDTO book){
       book.setId(0);
       bookRepository.save(bookDTOConverter.convert(book));
    }

    @PutMapping
    void put(@RequestBody BookDTO book) {
        if (!bookRepository.existsById(book.getId())) {
            throw new NotFoundException(String.format("Book id %d is not found", book.getId()));
        }
        bookRepository.save(bookDTOConverter.convert(book));
    }
}
