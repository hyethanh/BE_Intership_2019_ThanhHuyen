package com.example.blog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    private Integer id;

    @NonNull
    @NotBlank(message = "Title is my mandatory")
    private String title;

    @NonNull
    @NotBlank(message = "Content is mandatory")
    private String content;

    @NonNull
    private Date date;

    @ManyToMany
    private Set<Tag> tags=new HashSet<>();

    @ManyToOne
    private Category category;

    @JsonIgnore
    @OneToMany
    private Set<Comment> comments = new HashSet<>();
}
