package com.example.bookshop.controller;

import com.example.bookshop.repository.AuthorRepository;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    AuthorRepository authorRepository;
    BookRepository bookRepository;
    GenreRepository genreRepository;

    @Autowired
    public AdminController(AuthorRepository authorRepository, BookRepository bookRepository, GenreRepository genreRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping
    public String adminPanel(Model model) {

        return "admin/admin-panel";
    }

    @GetMapping("/books/add")
    public String bookAdd(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());

        return "admin/admin-book-add";
    }

    @GetMapping("/books/update")
    public String bookUpdate(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());
        return "admin/admin-book-update";
    }

    @GetMapping("/books/delete")
    public String bookDelete(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "admin/admin-book-delete";
    }

    @GetMapping("/authors/add")
    public String authorAdd(Model model) {
        return "admin/admin-author-add";
    }

    @GetMapping("/authors/update")
    public String authorUpdate(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        return "admin/admin-author-update";
    }

    @GetMapping("/authors/delete")
    public String authorDelete(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        return "admin/admin-author-delete";
    }
}

