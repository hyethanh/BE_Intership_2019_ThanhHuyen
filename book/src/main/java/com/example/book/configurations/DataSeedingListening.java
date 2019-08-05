package com.example.book.configurations;

import com.example.book.models.Role;
import com.example.book.models.User;
import com.example.book.reposities.RoleReposity;
import com.example.book.reposities.UserReposity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Configuration
public class DataSeedingListening implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserReposity userReposity;

    @Autowired
    private RoleReposity roleRepository;

    @Value("${jwt-key}")
    private String signingKey;

    private void addRoleIfMissing(String name){
        if(roleRepository.findByName(name)==null){
            roleRepository.save(new Role(name));
        }
    }

    private void addUserIfMissing(String email, String password, String... roles){
        if(userReposity.findByEmail(email)==null){
            User user  = new User(email,new BCryptPasswordEncoder().encode(password),true);
            user.setRoles(new HashSet<>());

            for(String role:roles){
                user.getRoles().add(roleRepository.findByName(role));
            }
            userReposity.save(user);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){

        addRoleIfMissing("admin");
        addRoleIfMissing("user");

        addUserIfMissing("user@gmail.com","user","user");
        addUserIfMissing("admin@gmail.com","admin","useradmin","user","admin");

        if(signingKey==null||signingKey.length()==0){
            String jws = Jwts.builder().setSubject("blog")
                    .signWith(SignatureAlgorithm.HS256,"BlogApi").compact();

            System.out.println("Use this jwt key:");
            System.out.println("jwt-key=" + jws);

        }
    }
}