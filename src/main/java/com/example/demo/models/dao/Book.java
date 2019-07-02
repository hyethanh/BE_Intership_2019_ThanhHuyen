package com.example.demo.models.dao;

import com.example.demo.models.Category;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    private int id;

    @NonNull
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Max(value = 2100,message = "Invalid year")
    private int year;

    @ManyToOne
    private Category category;

}
