package com.example.bookshop.dto;

import com.example.bookshop.model.Author;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookAddDTO {
    private String name;
    private Integer price;
    private String image;
    private LocalDate published_data;
    private int age_restriction;
    private int page_count;
    private String description;
    private Double raiting;
    private Integer read_count;
    private Integer purchased_count;
    private Set<Integer> genres;
    private Set<Integer> authors;

    public BookAddDTO() {

    }

    public BookAddDTO(Book book) {
        this.name = book.getName();
        this.price = book.getPrice();
        this.image = book.getImage();
        this.published_data = book.getPublished_data();
        this.age_restriction = book.getAge_restriction();
        this.page_count = book.getPage_count();
        this.description = book.getDescription();
        this.raiting = book.getRaiting();
        this.read_count = book.getRead_count();
        this.purchased_count = book.getPurchased_count();
        this.genres = book.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());
        this.authors = book.getAuthors().stream().map(Author::getId).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "BookAddDTO{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", published_data='" + published_data + '\'' +
                ", age_restriction=" + age_restriction +
                ", page_count=" + page_count +
                ", description='" + description + '\'' +
                ", raiting=" + raiting +
                ", read_count=" + read_count +
                ", purchased_count=" + purchased_count +
                ", genres=" + genres +
                ", authors=" + authors +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate  getPublished_data() {
        return published_data;
    }

    public void setPublished_data(LocalDate  published_data) {
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

    public Double getRaiting() {
        return raiting;
    }

    public void setRaiting(Double raiting) {
        this.raiting = raiting;
    }

    public Integer getRead_count() {
        return read_count;
    }

    public void setRead_count(Integer read_count) {
        this.read_count = read_count;
    }

    public Integer getPurchased_count() {
        return purchased_count;
    }

    public void setPurchased_count(Integer purchased_count) {
        this.purchased_count = purchased_count;
    }

    public Set<Integer> getGenres() {
        return genres;
    }

    public void setGenres(Set<Integer> genres) {
        this.genres = genres;
    }

    public Set<Integer> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Integer> authors) {
        this.authors = authors;
    }
}
