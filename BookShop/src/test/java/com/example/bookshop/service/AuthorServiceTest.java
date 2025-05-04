package com.example.bookshop.service;

import com.example.bookshop.model.Author;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import com.example.bookshop.AbstractTest;
import com.example.bookshop.AuthorTestData;
import com.example.bookshop.util.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorServiceTest extends AbstractTest {
    @Autowired
    private ApplicationContext context;

    AuthorService authorService;

    @PostConstruct
    public void init() {
        authorService = context.getBean(AuthorService.class);
    }

    @Test
    void get() {
        Author actual = authorService.findById(AuthorTestData.AUTHOR1_ID);
        assertThat(actual).usingRecursiveComparison().comparingOnlyFields("id", "name", "surname").isEqualTo(AuthorTestData.author_id1);
    }

    @Test
    void getFiltered() {
        List<Author> actual = authorService.findBySurname("Пушкин");
        assertThat(actual).size().isEqualTo(1);
    }

    @Test
    void getNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> authorService.findById(AuthorTestData.NOT_FOUND_ID));
    }

    @Test
    void update() {
        Author updated = AuthorTestData.getUpdated();
        authorService.update(updated);
        Author actual = authorService.findById(updated.getId());
        assertThat(actual).usingRecursiveComparison().ignoringFields("books").isEqualTo(updated);
    }

    @Test
    void create() {
        Author created = authorService.create(AuthorTestData.getNew());
        int newId = created.getId();
        Author newAuthor = AuthorTestData.getNew();
        newAuthor.setId(newId);
        assertThat(created).usingRecursiveComparison().ignoringFields("books").isEqualTo(newAuthor);
        assertThat(authorService.findById(newId)).usingRecursiveComparison().ignoringFields("books").isEqualTo(newAuthor);
    }

    @Test
    void delete() {
        authorService.delete(AuthorTestData.AUTHOR1_ID);
        Assertions.assertThrows(NotFoundException.class, () -> authorService.findById(AuthorTestData.AUTHOR1_ID));
    }

    @Test
    void deleteNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> authorService.delete(AuthorTestData.NOT_FOUND_ID));
    }

    @Test
    void updateDuplicatedName() {
        Author duplicatedAuthor = AuthorTestData.getDuplicatedName();
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> authorService.update(duplicatedAuthor));
    }

    @Test
    void createWithException() {
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> authorService.create(AuthorTestData.doubleNewAuthor));
        validateCause(DataIntegrityViolationException.class, "authors_unique_name_idx", () -> authorService.create(AuthorTestData.doubleNewAuthor));
        validateRootCause(ConstraintViolationException.class, () -> authorService.create(new Author(null, "Test", null)));
        validateRootCause(ConstraintViolationException.class, () -> authorService.create(new Author(null, "Test", "")));
        validateRootCause(ConstraintViolationException.class, () -> authorService.create(new Author(null, null, "Test")));
    }
}