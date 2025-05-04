package com.example.bookshop.controller;

import com.example.bookshop.model.Order;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.AuthorRepository;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.GenreRepository;
import com.example.bookshop.repository.OrderRepository;
import com.example.bookshop.service.OrderService;
import com.example.bookshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    OrderService orderService;
    UserService userService;

    OrderRepository orderRepository;
    AuthorRepository authorRepository;
    BookRepository bookRepository;
    GenreRepository genreRepository;

    @Autowired
    public AdminController(AuthorRepository authorRepository, BookRepository bookRepository, GenreRepository genreRepository, OrderRepository orderRepository, UserService userService, OrderService orderService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.orderService = orderService;
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

    @GetMapping("/genres/add")
    public String genreAdd(Model model) {
        return "admin/admin-genre-add";
    }

    @GetMapping("/genres/update")
    public String genreUpdate(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        return "admin/admin-genre-update";
    }

    @GetMapping("/genres/delete")
    public String genreDelete(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        return "admin/admin-genre-delete";
    }

    @GetMapping("/orders/list")
    public String orderList(Model model) {
//        List<User> users = userService.getAll();
//        model.addAttribute("users", users);

        List<Order> allOrders = orderService.findAll();

        List<Order> pendingOrders = allOrders.stream()
                .filter(o -> !"Оплачен".equalsIgnoreCase(o.getStatus()))
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());

        List<Order> paidOrders = allOrders.stream()
                .filter(o -> "Оплачен".equalsIgnoreCase(o.getStatus()))
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());

        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("paidOrders", paidOrders);

        return "admin/admin-order-list";
    }

    @GetMapping("/orders/update")
    public String orderUpdate(Model model) {
        return "admin/admin-order-update";
    }
}

