package com.example.demo.models;

        import com.example.demo.models.dao.Book;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
        import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.test.context.junit4.SpringRunner;
        import org.springframework.test.context.web.WebAppConfiguration;

        import javax.validation.Validator;

        import static org.junit.Assert.assertFalse;
        import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class BookTest {

    @Autowired
    private Validator validator;

    @Test
    public void test_book_OK(){
        Book book = new Book();
        book.setName("test_book_ok");
        book.setYear(2010);
        assertTrue(validator.validate(book).isEmpty());
    }

    @Test
    public void test_book_above_2100(){
        Book book = new Book();
        book.setName("Above_2100");
        book.setYear(2101);
        assertFalse(validator.validate(book).isEmpty());
    }

    @Test
    public void test_book_invalid_name(){
        Book book = new Book();
        book.setYear(2000);
        book.setName("");
        assertFalse(validator.validate(book).isEmpty());
    }
}
