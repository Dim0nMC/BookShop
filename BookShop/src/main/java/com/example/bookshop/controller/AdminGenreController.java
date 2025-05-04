package com.example.bookshop.controller;


import com.example.bookshop.dto.AuthorUpdateDTO;
import com.example.bookshop.dto.GenreUpdateDTO;
import com.example.bookshop.model.Author;
import com.example.bookshop.model.Genre;
import com.example.bookshop.repository.AuthorRepository;
import com.example.bookshop.repository.GenreRepository;
import com.example.bookshop.service.AuthorService;
import com.example.bookshop.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/genres")
public class AdminGenreController {

    @Autowired
    private GenreService genreService;
    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/{id}")
    public ResponseEntity<GenreUpdateDTO> get (@PathVariable int id) {
        Genre genre = genreService.findById(id);
        return ResponseEntity.ok(new GenreUpdateDTO(genre));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody Genre genre) {
        genreService.create(genre);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Genre genre) {
        genreService.update(genre);
    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable int id) {
        genreService.delete(id);
    }
}
