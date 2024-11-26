package com.example.bookshop.model;

import jakarta.persistence.*;


@Entity
@Table
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
}
