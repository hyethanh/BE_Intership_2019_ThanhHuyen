package com.example.demo.services;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.UserReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component

public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserReposity userReposity;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        User user = userReposity.findByUserName(username);

        if(user==null){
            throw new UsernameNotFoundException("User is not found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        Set<Role> roles = user.getRole();

        for(Role role:roles){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getPassword(), grantedAuthorities);
    }
}
