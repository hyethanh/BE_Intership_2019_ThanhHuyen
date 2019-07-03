package com.example.blog.controllers;

import com.example.blog.exceptions.NotFoundException;
import com.example.blog.models.Category;
import com.example.blog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/{id}")
    Category get(@PathVariable int id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        throw new NotFoundException(String.format("Category id %d is not found", id));
    }

    @GetMapping
    Iterable<Category> get() {
        return categoryRepository.findAll(Sort.by("id"));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format("Category id %d is not found", id));
        }
    }

    @PostMapping
    void post(@RequestBody @Validated Category category) {
        category.setId(0);
        categoryRepository.save(category);
    }

    @PutMapping
    void put(@RequestBody Category category) {
        categoryRepository.save(category);
    }
}
