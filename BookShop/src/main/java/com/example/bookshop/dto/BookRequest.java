package com.example.bookshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Set;

public class BookRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("image")
    private String image;

    @JsonProperty("published_date")
    private LocalDate publishedDate;

    @JsonProperty("age_restriction")
    private int ageRestriction;

    @JsonProperty("page_count")
    private int pageCount;

    @JsonProperty("description")
    private String description;

    @JsonProperty("raiting")
    private Double raiting;

    @JsonProperty("read_count")
    private Integer readCount;

    @JsonProperty("purchased_count")
    private Integer purchasedCount;

    @JsonProperty("authors")
    private Set<Integer> authors;

    @JsonProperty("genres")
    private Set<Integer> genres;



    // Getters и Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
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

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getPurchasedCount() {
        return purchasedCount;
    }

    public void setPurchasedCount(Integer purchasedCount) {
        this.purchasedCount = purchasedCount;
    }

    public Set<Integer> getAuthors() {
        return authors;
    }

    public void setAuthorNames(Set<Integer> authors) {
        this.authors = authors;
    }

    public Set<Integer> getGenres() {
        return genres;
    }

    public void setGenres(Set<Integer> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "BookRequest{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", publishedDate=" + publishedDate +
                ", ageRestriction=" + ageRestriction +
                ", pageCount=" + pageCount +
                ", description='" + description + '\'' +
                ", raiting=" + raiting +
                ", readCount=" + readCount +
                ", purchasedCount=" + purchasedCount +
                ", authors=" + authors +
                ", genres=" + genres +
                '}';
    }
}
