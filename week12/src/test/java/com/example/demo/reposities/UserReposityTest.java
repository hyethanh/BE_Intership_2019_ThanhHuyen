package com.example.demo.reposities;

import com.example.demo.models.User;
import com.example.demo.repositories.UserReposity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})

public class UserReposityTest {

    @Autowired
    private UserReposity userReposity;

    @Test
    public void test_findByUserName(){

        userReposity.save(new User("abc","First Name","Last Name","123"));
        assertNotNull(userReposity.findByUserName("abc"));
        assertNull(userReposity.findByUserName("fff"));
    }
}
