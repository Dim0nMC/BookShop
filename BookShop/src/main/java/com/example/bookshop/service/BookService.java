package com.example.bookshop.service;

import com.example.bookshop.model.Author;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Genre;
import com.example.bookshop.repository.AuthorRepository;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.GenreRepository;
import com.example.bookshop.dto.BookRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {

    BookRepository bookRepository;
    AuthorRepository authorRepository;
    GenreRepository genreRepository;


    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

       public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void create(BookRequest bookRequest) {
        Book newBook = new Book();
        newBook.setName(bookRequest.getName());
        newBook.setPublished_data(bookRequest.getPublishedDate());
        newBook.setAge_restriction(bookRequest.getAgeRestriction());
        newBook.setPage_count(bookRequest.getPageCount());
        newBook.setDescription(bookRequest.getDescription());
        newBook.setRaiting(bookRequest.getRaiting() != null ? bookRequest.getRaiting() : 0.0);
        newBook.setRead_count(bookRequest.getReadCount() != null ? bookRequest.getReadCount() : 0);
        newBook.setPurchased_count(bookRequest.getPurchasedCount() != null ? bookRequest.getPurchasedCount() : 0);

        bookRepository.save(newBook);
//        Set<Integer> authorsID = bookRequest.getAuthors();
//        Set<Author> authorObjects = new HashSet<>();
//        for(Integer authorID : authorsID) {
//            Author author = authorRepository.findById(authorID).orElse(null);
//            System.out.println(author.toString());
//            authorObjects.add(author);
//        }
//
//
//        Set<Integer> genresID = bookRequest.getGenres();
//        Set<Genre> genreObjects = new HashSet<>();
//        for(Integer genreID : genresID) {
//            Genre genre = genreRepository.findById(genreID).orElse(null);
//            System.out.println(genre.toString());
//            genreObjects.add(genre);
//        }

        Set<Author> authorObjects = new HashSet<>(authorRepository.findAllById(bookRequest.getAuthors()));
        Set<Genre> genreObjects = new HashSet<>(genreRepository.findAllById(bookRequest.getGenres()));

        if (authorObjects.size() != bookRequest.getAuthors().size()) {
            throw new IllegalArgumentException("Some authors not found.");
        }

        if (genreObjects.size() != bookRequest.getGenres().size()) {
            throw new IllegalArgumentException("Some genres not found.");
        }

        System.out.println(authorObjects.toString());
        newBook.setAuthors(authorObjects);
        System.out.println(genreObjects.toString());
        newBook.setGenres(genreObjects);



        bookRepository.save(newBook);
    }

}
