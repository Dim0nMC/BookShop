package com.example.bookshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowPageController {
    @GetMapping("/rest/admin/book/add")
    public String showAddBookForm() {
        System.out.println("showAddBookForm");
        return "addBook";
    }
}
