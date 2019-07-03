package com.example.blog.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    @NonNull
    private String username;

    @JsonAlias("first_name")
    @Column
    @NonNull
    private String firstName;

    @JsonAlias("last_name")
    private String lastName;

    @Column(nullable = false)
    @NonNull
    private String password;
    }
