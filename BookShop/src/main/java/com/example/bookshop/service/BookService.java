package com.example.bookshop.service;

import com.example.bookshop.model.Author;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Genre;
import com.example.bookshop.repository.AuthorRepository;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.GenreRepository;
import com.example.bookshop.dto.BookRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;


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

    public List<Book> findByPart(String query) {
        List<Book> books = new ArrayList<>();
        String[] parts = query.split(" ");
        //for (String part : parts) {
            books.addAll(bookRepository.findByNameContainingIgnoreCase(query));
            books.addAll(bookRepository.findByAuthors_NameContainingIgnoreCaseOrAuthors_SurnameContainingIgnoreCase(query,query));
            books.addAll(bookRepository.findByGenres_NameContainingIgnoreCase(query));
        //}

        return books;
    }


    private Book createFromDto(BookRequest bookRequest) {
        Book book = new Book();
        book.setName(bookRequest.getName());
        book.setPublished_data(bookRequest.getPublishedDate());
        book.setAge_restriction(bookRequest.getAgeRestriction());
        book.setPage_count(bookRequest.getPageCount());
        book.setDescription(bookRequest.getDescription());
        book.setRaiting(bookRequest.getRaiting() != null ? bookRequest.getRaiting() : 0.0);
        book.setRead_count(bookRequest.getReadCount() != null ? bookRequest.getReadCount() : 0);
        book.setPurchased_count(bookRequest.getPurchasedCount() != null ? bookRequest.getPurchasedCount() : 0);

        return book;
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

    @Transactional
    public Book update(BookRequest bookRequest) {
        Assert.notNull(bookRequest, "Book must not be null");
        Assert.notNull(bookRequest.getId(), "Book id must not be null");

        Book updatedBook = bookRepository.findById(bookRequest.getId()).orElse(null);

        updatedBook.setName(bookRequest.getName());
        updatedBook.setPublished_data(bookRequest.getPublishedDate());
        updatedBook.setAge_restriction(bookRequest.getAgeRestriction());
        updatedBook.setPage_count(bookRequest.getPageCount());
        updatedBook.setDescription(bookRequest.getDescription());
        updatedBook.setRaiting(bookRequest.getRaiting() != null ? bookRequest.getRaiting() : 0.0);
        updatedBook.setRead_count(bookRequest.getReadCount() != null ? bookRequest.getReadCount() : 0);
        updatedBook.setPurchased_count(bookRequest.getPurchasedCount() != null ? bookRequest.getPurchasedCount() : 0);

        Set<Author> authorObjects = new HashSet<>(authorRepository.findAllById(bookRequest.getAuthors()));
        Set<Genre> genreObjects = new HashSet<>(genreRepository.findAllById(bookRequest.getGenres()));

        if (authorObjects.size() != bookRequest.getAuthors().size()) {
            throw new IllegalArgumentException("Some authors not found.");
        }

        if (genreObjects.size() != bookRequest.getGenres().size()) {
            throw new IllegalArgumentException("Some genres not found.");
        }

        System.out.println(authorObjects.toString());
        updatedBook.setAuthors(authorObjects);
        System.out.println(genreObjects.toString());
        updatedBook.setGenres(genreObjects);

        return bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));

        for (Author author : book.getAuthors()) {
            author.getBooks().remove(book);
            authorRepository.save(author);
        }

        for (Genre genre : book.getGenres()) {
            genre.getBooks().remove(book);
            genreRepository.save(genre);
        }

        bookRepository.delete(book);
    }

}
