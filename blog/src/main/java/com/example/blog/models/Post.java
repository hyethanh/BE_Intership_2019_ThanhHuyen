package com.example.blog.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
    private int id;

    @NonNull
    @NotBlank(message = "Title is my mandatory")
    private String title;

    @NonNull
    @NotBlank(message = "Context is mandatory")
    private String context;

    @ManyToMany
    List<Tag> tag;

    @ManyToOne
    private Category category;
}
