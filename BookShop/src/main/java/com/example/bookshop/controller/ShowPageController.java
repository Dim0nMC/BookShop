package com.example.bookshop.controller;

import com.example.bookshop.model.Book;
import com.example.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShowPageController {

    private final BookService bookService;

    @Autowired
    public ShowPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String showIndex(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        System.out.println(books.get(0).toString());
        return "index";
    }

    @GetMapping("/rest/admin/book")
    public String showAddBookForm() {
        System.out.println("showAddBookForm");
        return "addBook";
    }
}
