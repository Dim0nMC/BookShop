package com.example.bookshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "books")
public class Book extends AbstractBaseEntity{

    @ManyToMany
    @JoinTable(
            name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Author> authors;

    @ManyToMany(mappedBy = "books")
    private Set<User> users;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetails> orderDetailsSet;

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    private String image;

    @NotNull
    private LocalDate published_data;

    @NotNull
    @Min(value = 0)
    @Max(value = 21)
    private int age_restriction;

    @NotNull
    @Min(value = 1)
    private int page_count;

    @Size(max = 10000)
    private String description;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private double raiting;

    @NotNull
    private int read_count;

    @NotNull
    private int purchased_count;

    public Book() {}

    public Book(Set<Genre> genres, Set<Author> authors, String name, LocalDate published_data, int age_restriction, int page_count, double raiting, int read_count, int purchased_count) {
        this.genres = genres;
        this.authors = authors;
        this.name = name;
        this.published_data = published_data;
        this.age_restriction = age_restriction;
        this.page_count = page_count;
        this.raiting = 0;
        this.read_count = 0;
        this.purchased_count = 0;
    }

    public Book(Set<Genre> genres, Set<Author> authors, Set<User> users, Set<OrderDetails> orderDetailsSet, String name, String image, LocalDate published_data, int age_restriction, int page_count, String description, double raiting, int read_count, int purchased_count) {
        this.genres = genres;
        this.authors = authors;
        this.users = users;
        this.orderDetailsSet = orderDetailsSet;
        this.name = name;
        this.image = image;
        this.published_data = published_data;
        this.age_restriction = age_restriction;
        this.page_count = page_count;
        this.description = description;
        this.raiting = raiting;
        this.read_count = read_count;
        this.purchased_count = purchased_count;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<OrderDetails> getOrderDetailsSet() {
        return orderDetailsSet;
    }

    public void setOrderDetailsSet(Set<OrderDetails> orderDetailsSet) {
        this.orderDetailsSet = orderDetailsSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getPublished_data() {
        return published_data;
    }

    public void setPublished_data(LocalDate published_data) {
        this.published_data = published_data;
    }

    public int getAge_restriction() {
        return age_restriction;
    }

    public void setAge_restriction(int age_restriction) {
        this.age_restriction = age_restriction;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRaiting() {
        return raiting;
    }

    public void setRaiting(double raiting) {
        this.raiting = raiting;
    }

    public int getRead_count() {
        return read_count;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
    }

    public int getPurchased_count() {
        return purchased_count;
    }

    public void setPurchased_count(int purchased_count) {
        this.purchased_count = purchased_count;
    }
}
