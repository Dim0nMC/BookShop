package com.example.bookshop.dto;

import com.example.bookshop.model.Genre;

public class GenreUpdateDTO {

    private int id;
    private String name;

    public GenreUpdateDTO() {}

    public GenreUpdateDTO(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
