package com.example.bookshop.repository;

import com.example.bookshop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameContainingIgnoreCase(String namePart);
    List<Book>  findByAuthors_NameContainingIgnoreCaseOrAuthors_SurnameContainingIgnoreCase(String authorNamePart , String authorSurnamePart);
}
