package com.example.bookshop.controller;

import com.example.bookshop.service.BookService;
import com.example.bookshop.tdo.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController//(value = AdminBookRestController.REST_URL)
public class AdminBookRestController {
    static final String REST_URL = "/rest/admin/book";

    @Autowired
    private BookService bookService;

    @PostMapping("/rest/admin/book/add")
    public String addBook(@RequestBody BookRequest book) {
        System.out.println("Метод контроллера вызван");
        System.out.println(book.toString());
        bookService.create(book);
        return "success";
    }


}
