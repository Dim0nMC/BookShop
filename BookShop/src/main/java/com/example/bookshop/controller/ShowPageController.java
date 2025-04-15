package com.example.bookshop.controller;

import com.example.bookshop.dto.BookResponse;
import com.example.bookshop.model.Book;
import com.example.bookshop.service.BookService;
import com.example.bookshop.util.BookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<BookResponse> books = BookUtil.getBookResponse(bookService.getAll(),12);
        model.addAttribute("books", books);
        System.out.println(books.get(0).toString());
        return "index";
    }

    @GetMapping("/rest/admin/book")
    public String showAddBookForm() {
        System.out.println("showAddBookForm");
        return "addBook";
    }

    @GetMapping("/search")
    public String showSearchForm(@RequestParam("query") String query, Model model) {
        List<BookResponse> books = BookUtil.getBookResponse(bookService.findByPart(query));
        model.addAttribute("books", books);
        model.addAttribute("query", query);
        //System.out.println(books.get(0).toString());
        System.out.println(query);

        return "search";
    }
}
