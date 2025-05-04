package com.example.bookshop.service;

import com.example.bookshop.AbstractTest;
import com.example.bookshop.GenreTestData;
import com.example.bookshop.model.Genre;
import com.example.bookshop.util.exception.NotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GenreServiceTest extends AbstractTest {

    @Autowired
    private ApplicationContext context;

    private GenreService genreService;

    @PostConstruct
    public void init() {
        genreService = context.getBean(GenreService.class);
    }

    @Test
    void findById() {
        Genre genre = genreService.findById(GenreTestData.GENRE1_ID);
        assertThat(genre).isEqualTo(GenreTestData.genre1);
    }

    @Test
    void findByIdNotFound() {
        assertThrows(NotFoundException.class, () -> genreService.findById(GenreTestData.NOT_FOUND_ID));
    }

    @Test
    void create() {
        Genre newGenre = GenreTestData.getNew();
        Genre created = genreService.create(newGenre);
        int newId = created.getId();
        newGenre.setId(newId);
        assertThat(created).isEqualTo(newGenre);
        assertThat(genreService.findById(newId)).isEqualTo(newGenre);
    }

    @Test
    void createInvalid() {
        assertThrows(IllegalArgumentException.class, () -> genreService.create(null));
        validateRootCause(ConstraintViolationException.class, () -> genreService.create(new Genre(null, "")));
    }

    @Test
    void update() {
        Genre updated = GenreTestData.getUpdated();
        genreService.update(updated);
        Genre actual = genreService.findById(updated.getId());
        assertThat(actual).isEqualTo(updated);
    }

    @Test
    void updateInvalid() {
        assertThrows(IllegalArgumentException.class, () -> genreService.update(null));
        assertThrows(IllegalArgumentException.class, () -> genreService.update(new Genre(null, "Без id")));
    }

    @Test
    void delete() {
        genreService.delete(GenreTestData.GENRE1_ID);
        assertThrows(NotFoundException.class, () -> genreService.findById(GenreTestData.GENRE1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> genreService.delete(GenreTestData.NOT_FOUND_ID));
    }
}
