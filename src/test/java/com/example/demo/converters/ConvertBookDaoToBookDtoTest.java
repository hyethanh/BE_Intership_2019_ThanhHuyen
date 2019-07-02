package com.example.demo.converters;

import com.example.demo.converters.bases.ConvertBookDaoToBookDto;
import com.example.demo.converters.bases.Converter;
import com.example.demo.models.Category;
import com.example.demo.models.dao.Book;
import com.example.demo.models.dto.BookDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class ConvertBookDaoToBookDtoTest {

    @Autowired
    private Converter<Book, BookDTO> bookDaoToBookDto;

    @Test
    public void test_Convert() {

        Category category = new Category();
        category.setIdCate(8);

        Book book = new Book();
        book.setCategory(category);
        book.setName("Book 1");
        book.setId(20);
        book.setYear(2000);

        BookDTO bookDTO = bookDaoToBookDto.convert(book);

        assertEquals(bookDTO.getId(), 20);
        assertEquals(bookDTO.getName(), "Book 1");
        assertEquals(bookDTO.getIdCate(), 8);
        assertEquals(bookDTO.getYear(), 2000);

    }
}
