package com.example.bookshop.service;

import com.example.bookshop.model.Genre;
import com.example.bookshop.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.example.bookshop.util.ValidationUtil.checkNotFoundWithId;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre findById(int id) {
        return checkNotFoundWithId(genreRepository.findById(id).orElse(null), id);
    }

    public Genre create(Genre genre) {
        Assert.notNull(genre, "Genre must not be null");
        return genreRepository.save(genre);
    }

    public Genre update(Genre genre) {
        Assert.notNull(genre, "Genre must not be null");
        Assert.notNull(genre.getId(), "Genre id must not be null");
        return genreRepository.save(genre);
    }

    public void delete(int id) {
        checkNotFoundWithId(genreRepository.delete(id) !=0 ,id);
    }

}
