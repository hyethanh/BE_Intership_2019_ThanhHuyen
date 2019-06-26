package com.example.demo.controllers;

import com.example.demo.configurations.TokenProvider;
import com.example.demo.dao.Login;
import com.example.demo.models.Book;
import com.example.demo.models.Category;
import com.example.demo.repositories.BookRepository;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})

public class BookControllerTest {

    @Autowired
    private BookRepository bookRepository;

    private Book book1;
    private Book book2;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    private String token;

    @Before
    public void init() throws Exception {
        /*Gson gson = new Gson();
        Login bookPost = new Login();
        bookPost.setUserName("admin");
        bookPost.setPassword("1234");

        String json = gson.toJson(bookPost);

        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());*/

        book1 = bookRepository.save(new Book(1, "English"));
        book2 = bookRepository.save(new Book(2, "Mathematics"));

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("admin", "1234"));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        token = "Bearer " + jwtTokenUtil.generateToken(authentication);
    }

    @After
    public void destroy() {
        bookRepository.deleteAll();
    }

    @Test
    public void test_getAllBook() throws Exception {
        mockMvc.perform(get("/api/books").header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(book1.getId())))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("English")))
                .andExpect(jsonPath("$[1].id", Matchers.equalTo(book2.getId())))
                .andExpect(jsonPath("$[1].name", Matchers.equalTo("Mathematics")));
    }

    @Test
    public void test_getBook_Found() throws Exception {
        mockMvc.perform(get("/api/books/" + book2.getId()).header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.equalTo(book2.getId())))
                .andExpect(jsonPath("$.name", Matchers.equalTo(book2.getName())));
    }

    @Test
    public void test_getBook_NotFound() throws Exception {
        mockMvc.perform(get("/api/books/" + (book2.getId() + book1.getId())).header("Authorization", token))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser("admin")
    public void test_deleteBook_NotFound() throws Exception {
        mockMvc.perform(delete("/api/books/" + (book2.getId() + book1.getId())).header("Authorization", token))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_deleteBook_Found() throws Exception {
        mockMvc.perform(delete("/api/books/" + book1.getId()).header("Authorization", token))
                .andExpect(status().isOk());

        assertFalse(bookRepository.findById(book1.getId()).isPresent());
    }

    @Test
    public void test_put_ok() throws Exception {

        Gson gson = new Gson();
        String json = gson.toJson(new Book(book1.getId(), "test_put_ok").setYear(1999));

        mockMvc.perform(put("/api/books").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());


        Optional<Book> book = bookRepository.findById(book1.getId());
        assertTrue(book.isPresent());
        assertEquals(book.get().getName(), "test_put_ok");
    }

    @Test
    public void test_post_ok() throws Exception{
        Gson gson = new Gson();
        Book book = new Book();
        book.setYear(2001);
        book.setName("test_post_book");

        String json = gson.toJson(book);

        mockMvc.perform(post("/api/books").header("Authorization",token)
        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void test_post_not_ok() throws Exception {
        Gson gson = new Gson();
        Book book = new Book();

        mockMvc.perform(post("/api/books").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(book)))
                .andExpect(status().isBadRequest());

        book.setName("test_post_book");
        book.setYear(3000);
        mockMvc.perform(post("/api/books").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(book)))
                .andExpect(status().isBadRequest());
    }
}
