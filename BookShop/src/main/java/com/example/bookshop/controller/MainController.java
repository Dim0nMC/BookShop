package com.example.bookshop.controller;

import com.example.bookshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

//    @GetMapping("/")
//    public String home(Model model) {
//        model.addAttribute("message", "Привет из Spring!");
//        return "index";
//    }

//    @PostMapping("/submit-name")
//    public String submitName(@RequestParam String name, Model model) {
//        User user = new User(name);
//        userService.create(user);
//        return "home";
//    }
}
