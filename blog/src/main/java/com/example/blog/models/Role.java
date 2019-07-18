package com.example.blog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id=0;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column
    @NonNull
    private String description;

    public Role(@NonNull String name, @NonNull String description) {
        this.name = name;
        this.description = description;
    }
}
