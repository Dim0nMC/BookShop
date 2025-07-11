package com.example.bookshop;

import com.example.bookshop.model.Genre;

import static com.example.bookshop.AbstractTest.START_SEQ;

public class GenreTestData {
    public static final int GENRE1_ID = START_SEQ + 9;
    public static final int GENRE2_ID = START_SEQ + 8;
    public static final int NOT_FOUND_ID = START_SEQ + 100;

    public static final Genre genre1 = new Genre(GENRE1_ID, "рассказы");
    public static final Genre genre2 = new Genre(GENRE2_ID, "повесть");


    public static Genre getNew() {
        return new Genre(null, "Приключения");
    }

    public static Genre getUpdated() {
        return new Genre(GENRE1_ID, "Триллер");
    }
}
