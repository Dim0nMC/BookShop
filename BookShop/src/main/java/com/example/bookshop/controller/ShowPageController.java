package com.example.bookshop.controller;

import com.example.bookshop.dto.BookResponse;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Genre;
import com.example.bookshop.model.User;
import com.example.bookshop.model.Order;
import com.example.bookshop.service.*;
import com.example.bookshop.util.BookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ShowPageController {

    private final BookService bookService;
    private final GenreService genreService;
    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;

    @Autowired
    public ShowPageController(BookService bookService, GenreService genreService, UserService userService, CartService cartService, OrderService orderService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.userService = userService;
        this.cartService = new CartService();
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String showIndex(Model model) {
        List<BookResponse> books = new ArrayList<>();

        books.add(BookUtil.getBookResponse(bookService.getById(22)));
        books.add(BookUtil.getBookResponse(bookService.getById(21)));
        books.add(BookUtil.getBookResponse(bookService.getById(20)));
        books.add(BookUtil.getBookResponse(bookService.getById(27)));

        books.addAll(BookUtil.getBookResponse(bookService.getAll(),12));
        model.addAttribute("books", books);
        System.out.println(books.get(0).toString());
        return "index";
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

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html в templates
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // login.html в templates
    }

    @GetMapping("/profile/main")
    public String getProfileMain(Model model, Authentication authentication) {
        String username = authentication.getName();  // Получаем имя текущего пользователя
        User user = userService.getByEmail(username);
        model.addAttribute("user", user);
        System.out.println(user.getImage());// Передаем данные пользователя в модель
        return "profile-main";  // Вернем представление страницы профиля
    }

    @GetMapping("/profile/books")
    public String getProfileBooks(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getByEmail(username);
        user.getBooks().forEach(System.out::println);
        Set<Book> books = user.getBooks();
        model.addAttribute("user", user);
        model.addAttribute("books", user.getBooks());
        System.out.println(user.getImage());
        return "profile-books";
    }

    @GetMapping("/profile/orders")
    public String getUserOrders(@AuthenticationPrincipal UserDetails userDetails, Authentication authentication, Model model) {
        String username = authentication.getName();  // Получаем имя текущего пользователя
        User user = userService.getByEmail(username);
        model.addAttribute("user", user);

        List<Order> allOrders = orderService.getOrdersByUser(user);
        List<Order> activeOrders = allOrders.stream()
                .filter(o -> !"Оплачен".equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());

        List<Order> completedOrders = allOrders.stream()
                .filter(o -> "Оплачен".equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());

        model.addAttribute("activeOrders", activeOrders);
        model.addAttribute("completedOrders", completedOrders);

        return "profile-orders";
    }


    @GetMapping("/profile/security")
    public String getProfileSecurity(Model model, Authentication authentication) {
        String username = authentication.getName();  // Получаем имя текущего пользователя
        User user = userService.getByEmail(username);
        model.addAttribute("user", user);
        System.out.println(user.getImage());// Передаем данные пользователя в модель
        return "profile-security";  // Вернем представление страницы профиля
    }

    @GetMapping("/profile/cart")
    public String showCart(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getByEmail(username);
        Set<BookResponse> cart = BookUtil.getBookResponse(user.getCart());
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", cartService.getTotalPrice(cart));
        return "cart";
    }



    //-------------------------------------TEST-----------------------------------------------

    @GetMapping("/test-auth")
    @ResponseBody
    public String testAuth(Authentication authentication) {
        if (authentication == null) return "Not logged in";
        return "Logged in as: " + authentication.getName() + " | Authorities: " + authentication.getAuthorities();
    }

}
