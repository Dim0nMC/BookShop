package com.example.bookshop.controller;


import com.example.bookshop.dto.AuthorUpdateDTO;
import com.example.bookshop.model.Author;
import com.example.bookshop.repository.AuthorRepository;
import com.example.bookshop.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/authors")
public class AdminAuthorController {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/{id}")
    public ResponseEntity<AuthorUpdateDTO> get (@PathVariable int id) {
        Author author = authorService.findById(id);
        return ResponseEntity.ok(new AuthorUpdateDTO(author));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody Author author) {
        authorService.create(author);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Author author) {
        authorService.update(author);
    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable int id) {
        authorService.delete(id);
    }
}
