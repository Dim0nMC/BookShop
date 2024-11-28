package com.example.bookshop.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderDetailsId implements Serializable {
    private long orderId;
    private long bookId;
}
