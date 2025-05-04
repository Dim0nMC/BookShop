package com.example.bookshop.service;

import com.example.bookshop.model.Genre;
import com.example.bookshop.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return genreRepository.findById(id).get();
    }

    public Genre create(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre update(Genre genre) {
        return genreRepository.save(genre);
    }

    public void delete(int id) {
        genreRepository.deleteById(id);
    }

}
