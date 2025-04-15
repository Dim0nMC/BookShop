package com.example.bookshop.util;

import com.example.bookshop.dto.BookResponse;
import com.example.bookshop.model.Author;
import com.example.bookshop.model.Book;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookUtil {

    public static BookResponse getBookResponse(Book book) {

        Iterator<Author> iterator = book.getAuthors().iterator();
        Author author = iterator.next();
        BookResponse bookResponse = new BookResponse(
                book.getId(),
                book.getName(),
                author.getName()+" "+author.getSurname(),
                book.getImage()
        );
        return bookResponse;
    }

    public static List<BookResponse> getBookResponse(List<Book> books) {
        List<BookResponse> bookResponses = new ArrayList<BookResponse>();
        for(Book book : books) {

            Iterator<Author> iterator = book.getAuthors().iterator();
            Author author = iterator.next();
            BookResponse bookResponse = new BookResponse(
                    book.getId(),
                    book.getName(),
                    author.getName()+" "+author.getSurname(),
                    book.getImage()
            );
            bookResponses.add(bookResponse);
        }
        return bookResponses;
    }

    public static List<BookResponse> getBookResponse(List<Book> books, int amount) {
        List<BookResponse> bookResponses = new ArrayList<BookResponse>();
        int i = 0;
        for(Book book : books) {
            if(i == amount) {
                break;
            }
            Iterator<Author> iterator = book.getAuthors().iterator();
            Author author = iterator.next();
            BookResponse bookResponse = new BookResponse(
                    book.getId(),
                    book.getName(),
                    author.getName()+" "+author.getSurname(),
                    book.getImage()
            );
            bookResponses.add(bookResponse);
            i++;
        }
        return bookResponses;
    }
}
