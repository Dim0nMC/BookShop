package com.example.bookshop.service;

import com.example.bookshop.model.*;
import com.example.bookshop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

//    public Order findByIdAndUser(Integer id, User user) {
//        return orderRepository.findByIdAndUser(id, user)
//                .orElseThrow(() -> new RuntimeException("Order not found or access denied"));
//    }

    public Order  create (User user, Set<Book> cartBooks) {
        Order order = new Order();
        order.setUser_id(user);
        order.setDate(LocalDate.now());
        order.setStatus("В обработке");

        double totalCost = cartBooks.stream()
                .mapToDouble(Book::getPrice)
                .sum();
        order.setCost(totalCost);

        Set<OrderDetails> detailsSet = new HashSet<>();

        for (Book book : cartBooks) {
            OrderDetails detail = new OrderDetails();
            detail.setBook(book);
            detail.setOrder(order);
            detail.setPrice(book.getPrice().doubleValue());


            OrderDetailsId id = new OrderDetailsId();
            id.setBookId(book.getId());
            // Order ID пока ещё не установлен (он появится после сохранения), можно оставить null или временно 0
            detail.setId(id);

            detailsSet.add(detail);
        }

        order.setOrderDetailsSet(detailsSet);

        return orderRepository.save(order);

    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser_id(user);
    }

    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }

    @Transactional
    public void payOrder(Integer orderId, User user) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus("Оплачен");
        order.setPayment_date(java.time.LocalDate.now());
        orderRepository.save(order);
        Set<Book> books = order.getOrderDetailsSet()
                .stream()
                .map(OrderDetails::getBook)
                .collect(Collectors.toSet());

        for (Book book : books) {
            user.getBooks().add(book);
        }

    }


}
