package com.example.demo.repositories;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface UserReposity extends JpaRepository<User,Integer> {

   // @Query(value="select * from user where concat(' ', first_name,' ', last_name, ' ') LIKE '%?1%'", nativeQuery = true)
   // User findByName(String name);

   User findByUserName(String userName);


}
