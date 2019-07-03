package com.example.demo.models.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
public class BookDTO {

    int id;

    @NotBlank(message = "Name is mandatory")
    String name;

    int idCate;

    @Max(value = 2100,message = "Invalid year")
    int year;
}
