package com.example.book.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NonNull
    @NotBlank
    private String title;

    @NonNull
    @NotBlank
    private String author;

    @Column
    private String description;

    @NonNull
    private Date createAt;

    @NonNull
    private Date updateAt;

    private String images;

    @NonNull
    private boolean enabled;

    @ManyToOne
    private User user;

}
