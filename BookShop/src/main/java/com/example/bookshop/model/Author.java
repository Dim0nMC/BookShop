package com.example.bookshop.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "authors")
public class Author extends AbstractBaseEntity {

    @JsonIgnore
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private Set<Book> books;

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @NotBlank
    @Size(min = 1, max = 100)
    private String surname;

    private String about;

    public Author(){}

    public Author(Integer id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public Author(Integer id, String name, String surname, String about) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.about = about;
    }

    public Author(Set<Book> books, String name, String surname, String about) {
        this.books = books;
        this.name = name;
        this.surname = surname;
        this.about = about;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
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

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", books=" + books +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", about='" + about + '\'' +
                '}';
    }
}
