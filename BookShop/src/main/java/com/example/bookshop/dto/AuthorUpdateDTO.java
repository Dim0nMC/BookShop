package com.example.bookshop.dto;

import com.example.bookshop.model.Author;

public class AuthorUpdateDTO {

    private int id;
    private String name;
    private String surname;
    private String about;

    public AuthorUpdateDTO() {}

    public AuthorUpdateDTO(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.surname = author.getSurname();
        this.about = author.getAbout();
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
