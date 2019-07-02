package com.example.demo.converters;

import com.example.demo.converters.bases.Converter;
import com.example.demo.models.Category;
import com.example.demo.models.dao.Book;
import com.example.demo.models.dto.BookDTO;
import com.example.demo.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class ConverBookDtoToBookDaoTest {

    @Autowired
    private Converter<BookDTO, Book> convertbookDTOBookDAO;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @Before
    public void init(){
        category=new Category();

        category.setNameCate("Comedy");
        categoryRepository.save(category);
    }

    @Test
    public void test_convert(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(11);
        bookDTO.setName("Book test 2");
        bookDTO.setYear(2002);

        Book book = convertbookDTOBookDAO.convert(bookDTO);
        assertEquals(book.getName(),"Book test 2");
        assertEquals(book.getId(),11);
        assertEquals(book.getYear(),2002);

        bookDTO.setIdCate(category.getIdCate());

        book=convertbookDTOBookDAO.convert(bookDTO);
        assertEquals(book.getCategory().getNameCate(),category.getNameCate());

    }

}
