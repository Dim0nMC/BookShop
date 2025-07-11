package com.example.bookshop.model;

import ch.qos.logback.core.model.INamedModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "genres")
@Schema(description = "All details about the Genre entity.")
public class Genre extends AbstractBaseEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;

    @NotBlank
    @Size(max = 50)
    @Schema(title = "The entity name")
    private String name;

    @ManyToMany(mappedBy = "genres",  fetch = FetchType.LAZY)
//    @Schema(title = "Set of Book entities of this Genre")
    private Set<Book> books;

    public Genre() {}

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String name, Set<Book> books) {
        this.name = name;
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

}
