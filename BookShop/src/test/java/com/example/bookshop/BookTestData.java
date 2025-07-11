package com.example.bookshop;

import com.example.bookshop.dto.BookUpdateDTO;
import com.example.bookshop.model.Author;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.example.bookshop.AbstractTest.START_SEQ;

public class BookTestData {
    public static final int BOOK1_ID = 8; //Архипелаг ГУЛАГ
    public static final int BOOK2_ID = 14; //Хулио Хуренио
    public static final int BOOK3_ID = 17;
    public static final int NOT_FOUND_ID = START_SEQ + 1000;
    public static final String PATH_VAR_FILTER_BY_PART = "пел";

    public static final int AUTHOR1_ID = 11; //Солженицин - Архипелаг ГУЛАГ
    public static final int AUTHOR2_ID = 15; //Пелевин - Хулио Хуренио

    public static final int GENRE11_ID = 1; //роман - Архипелаг ГУЛАГ
    public static final int GENRE12_ID = 6; //философия - Архипелаг ГУЛАГ

    public static final int GENRE21_ID = 7; //сатира - Хулио Хуренио
    public static final int GENRE22_ID = 6; //философия - Хулио Хуренио




    public static final Author author1= new Author(AUTHOR1_ID, "Александр", "Солженицин", "Автор \"Архипелага ГУЛАГ\"");
    public static final Author author2 = new Author(AUTHOR2_ID, "Виктор", "Пелевин", "Современный автор-фантаст");

    public static final Genre genre11 = new Genre(GENRE11_ID, "роман");
    public static final Genre genre12 = new Genre(GENRE12_ID, "философия");
    public static final Genre genre21 = new Genre(GENRE21_ID, "сатира");
    public static final Genre genre22 = new Genre(GENRE22_ID, "философия");



    public static final Book book1 = new Book(BOOK1_ID, Set.of(genre11, genre12), Set.of(author1),
            "Архипелаг ГУЛАГ", LocalDate.of(1973, 1, 1), 18,
            672, 5, 800, 200, 200,
            "Документальный роман об ужасах репрессий");
    public static final Book book2 = new Book(BOOK2_ID, Set.of(genre21, genre22), Set.of(author2),
            "Хулио Хуренио", LocalDate.of(2002, 1, 1), 18,
            180, 4, 450, 70, 500,
            "Философская сатира Пелевина");


    public static final BookUpdateDTO book_dto1 = new BookUpdateDTO(book1);
    public static final BookUpdateDTO book_dto2 = new BookUpdateDTO(book2);

    public static final List<BookUpdateDTO> find_books_dto = List.of(new BookUpdateDTO(book1), new BookUpdateDTO(book2));

    public static Book getNew() {
        Book newBook = new Book(null, Set.of(genre11, genre12), Set.of(author1),
                "Один день Ивана Денисовича", LocalDate.of(1959, 1, 1), 18,
                410, 5, 1000, 800, 400, "");
        newBook.setAuthors(Set.of(author1));
        newBook.setGenres(Set.of(genre11, genre12));
        return newBook;
    }

    public static BookUpdateDTO getNewDto() {
        return new BookUpdateDTO(getNew());
    }

    public static Book getUpdated() {
        Book updated = new Book(BOOK1_ID, Set.of(genre11, genre12), Set.of(author1),
                "Архипелаг ГУЛАГ", LocalDate.of(1977, 10, 10), 18,
                772, 5, 800, 200, 700, "");
        return updated;
    }

    public static BookUpdateDTO getUpdatedDto() {
        return new BookUpdateDTO(getUpdated());
    }


}
