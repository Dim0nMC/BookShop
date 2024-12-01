package com.example.bookshop.controller;

import com.example.bookshop.service.BookService;
import com.example.bookshop.dto.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminBookRestController.REST_URL)
public class AdminBookRestController {
    static final String REST_URL = "/rest/admin/book";

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public String addBook(@RequestBody BookRequest book) {
//        System.out.println("Метод контроллера вызван");
//        System.out.println(book.toString());
        bookService.create(book);
        return "success";
    }


}
