package com.example.demo.converters.bases;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.models.Category;
import com.example.demo.models.dao.Book;
import com.example.demo.models.dto.BookDTO;
import com.example.demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConvertBookDtoToBookDao extends Converter<BookDTO, Book> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Book convert(BookDTO source) {
        Book book = new Book();

        book.setName(source.getName());
        book.setYear(source.getYear());
        book.setId(source.getId());

        if (source.getIdCate() > 0) {
            Optional<Category> category = categoryRepository.findById(source.getIdCate());

            if (category.isPresent()) {
                book.setCategory(category.get());
            } else {
                throw new BadRequestException("Invalid category id" + source.getIdCate());
            }
        }

        return book;
    }
}
