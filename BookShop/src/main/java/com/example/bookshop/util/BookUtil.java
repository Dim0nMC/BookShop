package com.example.bookshop.util;

import com.example.bookshop.dto.BookResponse;
import com.example.bookshop.model.Author;
import com.example.bookshop.model.Book;

import java.util.Iterator;

public class BookUtil {

    public static BookResponse getBookResponse(Book book) {
        Iterator<Author> iterator = book.getAuthors().iterator();
        BookResponse bookResponse = new BookResponse(
                book.getId(),
                book.getName(),
                iterator.next().getName(),
                book.getImage()
        );
        return bookResponse;
    }
}
