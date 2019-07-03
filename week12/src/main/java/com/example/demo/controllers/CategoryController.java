package com.example.demo.controllers;

import com.example.demo.exceptions.NotFoundException;
import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/category")

public class CategoryController {

/*    @GetMapping("{/idcate}")
    Category get(@PathVariable int idcate) {
        Category cate = new Category();

        cate.setIdCate(idcate);
        cate.setNameCate("");

        return cate;
    }

    @GetMapping
    Category[] getListCate() {
        Category cate1 = new Category();
        cate1.setIdCate(1);
        cate1.setNameCate("Life");

        Category cate2 = new Category();
        cate2.setIdCate(2);
        cate2.setNameCate("Detective");

        return new Category[]{cate1, cate2};
    }

    @PutMapping()
    Category update(@RequestBody Category category) {
        System.out.print("" + category);
        return category;

    }*/

    /*@Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/{id}")
        //tim theo id
    Optional<Category> get(@PathVariable int id) {
        return categoryRepository.findById(id);
    }

    @GetMapping
        //tim het
    Iterable<Category> get() {
        return categoryRepository.findAll();
    }

    @DeleteMapping("/{id}")
        //xoa
    void delete(@PathVariable int idCate) {
        categoryRepository.deleteById(idCate);
    }

    @PostMapping()
        //add
    void post(@RequestBody Category category) {
        category.setIdCate(0);
        categoryRepository.save(category);
    }

    @PutMapping()
        //update
    void put(@RequestBody Category category) {
        categoryRepository.save(category);
    }*/

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
        return categoryRepository.findAll();
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new NotFoundException("Category id %d is not found");
        }
    }

    @PostMapping
    void post(@RequestBody Category category) {
        category.setIdCate(0);
        categoryRepository.save(category);
    }

    @PutMapping
    void put(@RequestBody Category category) {
        categoryRepository.save(category);
    }

}
