package com.example.bookshop.controller;

import com.example.bookshop.dto.BookResponse;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Genre;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.GenreService;
import com.example.bookshop.util.BookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ShowPageController {

    private final BookService bookService;
    private final GenreService genreService;

    @Autowired
    public ShowPageController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
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

    @GetMapping("/genres")
    public String showGenres(Model model) {
        List<Genre> genres = genreService.getAllGenres();
        model.addAttribute("genres", genres);

        return "genres";
    }

    @GetMapping("/book/{id}")
    public String showBookDetails(@PathVariable("id") int id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        System.out.println(book.toString());
        return "book-details";
    }

}
