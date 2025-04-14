INSERT INTO authors (id, name, surname, about)
VALUES
    ('1', 'Александр', 'Пушкин', '...'),
    ('2', 'Адольф', 'Гитлер', '...');


INSERT INTO genres (id, name)
VALUES
    ('1', 'драма'),
    ('2', 'романтика');

INSERT INTO books (id, name, image, description, read_count, raiting, published_data, purchased_count, page_count, age_restriction)
VALUES
    ('1', 'Meine Kampf', '...', 'База', '1', '5', '05.04.2025', '100', '69', '0'),
    ('2', 'Капитанская дочка', '...', 'qqq', '1', '5', '05.04.2025', '100', '69', '0'),
    ('3', 'Капитанская дочка', '...', 'aaa', '1', '5', '05.04.2025', '100', '69', '0'),
    ('4', 'Капитанская дочка', '...', '', '1', '5', '05.04.2025', '100', '69', '0');

INSERT INTO books_authors (author_id, book_id)
VALUES
    ('2', '1'),
    ('1', '2'),
    ('1', '3'),
    ('1', '4');

INSERT INTO books_genres (genre_id, book_id)
VALUES
    ('2', '1'),
    ('1', '2'),
    ('1', '3'),
    ('1', '4');
