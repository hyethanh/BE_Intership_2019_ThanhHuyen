package com.example.blog.configurations;

import com.example.blog.models.Role;
import com.example.blog.models.User;
import com.example.blog.repositories.RoleReposity;
import com.example.blog.repositories.UserReposity;
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

    private void addRoleIfMissing(String name,String description){
        if(roleRepository.findByName(name)==null){
            roleRepository.save(new Role(name,description));
        }
    }

    private void addUserIfMissing(String username, String password, String email, String... roles){
        if(userReposity.findByUsername(username)==null){
            User user  = new User(username,"fullName",new BCryptPasswordEncoder().encode(password),"email");
            user.setRoles(new HashSet<>());

            for(String role:roles){
                user.getRoles().add(roleRepository.findByName(role));
            }
            userReposity.save(user);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){

        addRoleIfMissing("admin","adminstrator");
        addRoleIfMissing("user","user");

        addUserIfMissing("user","789","user789","user");
        addUserIfMissing("user0","678","useradmin","user","admin");

        if(signingKey==null||signingKey.length()==0){
            String jws = Jwts.builder().setSubject("blog")
                    .signWith(SignatureAlgorithm.HS256,"BlogApi").compact();

            System.out.println("Use this jwt key:");
            System.out.println("jwt-key=" + jws);

        }
    }
}
