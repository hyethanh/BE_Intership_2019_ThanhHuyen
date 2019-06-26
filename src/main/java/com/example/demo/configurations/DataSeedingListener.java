package com.example.demo.configurations;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.RoleReposity;
import com.example.demo.repositories.UserReposity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;


@Component
@Configuration
@Profile({"!test"})

public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserReposity userReposity;

    @Autowired
    private RoleReposity roleReposity;

    @Value("${jwt-key}")
    private String signingKey;

    private void addRoleIfMissing(String name,String description){
        if(roleReposity.findByName(name)==null){
            roleReposity.save(new Role(name,description));
        }
    }

    private void addUserIfMissing(String userName, String password,String... roles){
        if(userReposity.findByUserName(userName)==null){
            User user =  new User(userName,"First Name","Last Name",new BCryptPasswordEncoder().encode(password));;
            user.setRole(new HashSet<>());

            for(String role:roles){
                user.getRole().add(roleReposity.findByName(role));
            }
            userReposity.save(user);
        }
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        addRoleIfMissing("ROLE_ADMIN","Adminstrators");
        addRoleIfMissing("ROLE_MEMBER","Members");

        addUserIfMissing("user","456","ROLE_MEMBER");
        addUserIfMissing("admin","1234","ROLE_MEMBER","ROLE_ADMIN");

        if(signingKey == null || signingKey.length() ==0){
            String jws = Jwts.builder()
                    .setSubject("BookStore")
                    .signWith(SignatureAlgorithm.HS256, "BookStoreApi").compact();

            System.out.println("Use this jwt key:");
            System.out.println("jwt-key=" + jws);
        }
    }
}
