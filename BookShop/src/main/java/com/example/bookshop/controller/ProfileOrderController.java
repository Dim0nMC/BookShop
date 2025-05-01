package com.example.bookshop.controller;

import com.example.bookshop.model.User;
import com.example.bookshop.service.OrderService;
import com.example.bookshop.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile/orders")
public class ProfileOrderController {

    private final OrderService orderService;
    private final UserService userService;

    public ProfileOrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/pay/{id}")
    public String payOrder(@PathVariable("id") Integer id, Authentication authentication) {
        String username = authentication.getName();  // Получаем имя текущего пользователя
        User user = userService.getByEmail(username);
        orderService.payOrder(id, user);
        return "redirect:/profile/orders";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer id) {
        orderService.deleteOrder(id);
        return "redirect:/profile/orders";
    }
}
