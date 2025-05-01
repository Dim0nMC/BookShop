package com.example.bookshop.controller;

import com.example.bookshop.model.Order;
import com.example.bookshop.model.User;
import com.example.bookshop.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {

    private final BookService bookService;
    private final GenreService genreService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public CartController(BookService bookService, GenreService genreService, UserService userService, OrderService orderService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.userService = userService;
        this.orderService = orderService;
    }


    @PostMapping("/cart/add/{id}")
    public String addCart(@PathVariable int id, Authentication authentication) {
        String username = authentication.getName();  // Получаем имя текущего пользователя
        User user = userService.getByEmail(username);
        user.getCart().add(bookService.getById(id));
        userService.addToCart(user);
        return "redirect:/profile/cart";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeCart(@PathVariable int id, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getByEmail(username);
        user.getCart().remove(bookService.getById(id));
        userService.update(user.getId(),user);
        return "redirect:/profile/cart";
    }

    @PostMapping("/cart/checkout")
    public String checkout(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getByEmail(username);
        Order order = orderService.create(user, user.getCart());
        user.getOrders().add(order);
        user.getCart().clear();
        userService.update(user.getId(),user);
        return "redirect:/profile/orders";
    }

}
