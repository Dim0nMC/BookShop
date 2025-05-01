package com.example.bookshop.service;

import com.example.bookshop.dto.BookResponse;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CartService {

    public int getTotalPrice(Set<BookResponse> books) {
        int totalPrice = 0;
        for(BookResponse book : books) {
            totalPrice += book.getPrice();
        }
        return totalPrice;
    }
}
