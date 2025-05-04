package com.example.bookshop;

import com.example.bookshop.model.Author;


import static com.example.bookshop.AbstractTest.START_SEQ;

public class AuthorTestData {
    public static final int AUTHOR1_ID = START_SEQ;
    public static final int NOT_FOUND_ID = START_SEQ + 100;

    public static final Author author_id1 = new Author(AUTHOR1_ID, "Александр", "Пушкин");
    public static final Author doubleNewAuthor = new Author(null, "Александр", "Пушкин");

    public static Author getNew() {
        Author newAuthor = new Author(null, "Рэй", "Брэдбери");
        newAuthor.setAbout("Американский писатель-фантаст");
        return newAuthor;
    }

    public static Author getUpdated() {
        Author updated = new Author(AUTHOR1_ID, author_id1.getName(), author_id1.getSurname());
        updated.setAbout("Лучший классик русской литературы");
        return updated;
    }

    public static Author getDuplicatedName() {
        return new Author(AUTHOR1_ID + 1, "Александр", "Пушкин");
    }
}
