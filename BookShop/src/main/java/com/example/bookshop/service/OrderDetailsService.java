package com.example.bookshop.service;

import com.example.bookshop.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService {

    private OrderService orderService;

    @Autowired
    public OrderDetailsService(OrderService orderService) {
        this.orderService = orderService;
    }

    public Order getById(int id) {
        return null;
    }
}
