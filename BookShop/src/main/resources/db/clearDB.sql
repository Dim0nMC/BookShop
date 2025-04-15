BEGIN;

DELETE FROM books_authors;
DELETE FROM books_genres;
DELETE FROM books;
DELETE FROM authors;
DELETE FROM genres;

COMMIT;
