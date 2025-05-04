DROP TABLE IF EXISTS books_genres;
DROP TABLE IF EXISTS books_authors;
DROP TABLE IF EXISTS books_users;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS order_details;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS genres;



DROP SEQUENCE IF EXISTS authors_seq;
DROP SEQUENCE IF EXISTS books_seq;
DROP SEQUENCE IF EXISTS users_seq;
DROP SEQUENCE IF EXISTS orders_seq;
DROP SEQUENCE IF EXISTS genres_seq;


CREATE SEQUENCE authors_seq START WITH 20;
CREATE SEQUENCE books_seq START WITH 1;
CREATE SEQUENCE users_seq START WITH 1;
CREATE SEQUENCE orders_seq START WITH 1;
CREATE SEQUENCE genres_seq START WITH 1;



CREATE TABLE authors (
    id INTEGER PRIMARY KEY DEFAULT nextval('authors_seq'),
    name character varying(100) NOT NULL,
    surname character varying(100) NOT NULL,
    about character varying(255)
);

CREATE INDEX author_name_idx ON authors(name);
CREATE UNIQUE INDEX authors_unique_name_idx ON authors(name, surname);




CREATE TABLE genres (
    id INTEGER PRIMARY KEY DEFAULT nextval('genres_seq'),
    name character varying(50) NOT NULL
);

CREATE UNIQUE INDEX genres_unique_name_idx ON genres(name);


CREATE TABLE books (
    id INTEGER PRIMARY KEY DEFAULT nextval('books_seq'),
    age_restriction integer NOT NULL,
    page_count integer NOT NULL,
    published_data date NOT NULL,
    purchased_count integer NOT NULL,
    raiting double precision NOT NULL,
    read_count integer NOT NULL,
    description character varying(10000),
    image character varying(255),
    name character varying(255) NOT NULL,
    price integer NOT NULL,
    --price integer,
    CONSTRAINT books_age_restriction_check CHECK (((age_restriction <= 21) AND (age_restriction >= 0))),
    CONSTRAINT books_page_count_check CHECK ((page_count >= 1)),
    CONSTRAINT books_raiting_check CHECK (((raiting <= (5)::double precision) AND (raiting >= (0)::double precision)))
);

CREATE INDEX books_name_idx ON books(name);



CREATE TABLE books_genres (
    book_id integer NOT NULL,
    genre_id integer NOT NULL,
    CONSTRAINT books_genres_pkey PRIMARY KEY (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres (id)
);

--ALTER TABLE ONLY books_genres ADD CONSTRAINT books_genres_pkey PRIMARY KEY (book_id, genre_id);





CREATE TABLE books_authors (
    author_id integer NOT NULL,
    book_id integer NOT NULL,
    CONSTRAINT books_authors_pkey PRIMARY KEY (author_id, book_id),
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors (id)
);

--ALTER TABLE ONLY books_authors ADD CONSTRAINT books_authors_pkey PRIMARY KEY (author_id, book_id);



CREATE TABLE roles (
    id integer PRIMARY KEY,
    name character varying(255) NOT NULL
);

CREATE UNIQUE INDEX roles_name_unique_idx ON roles(name);




CREATE TABLE users (
    id INTEGER PRIMARY KEY DEFAULT nextval('users_seq'),
    age integer NOT NULL,
    role_id integer NOT NULL,
    name character varying(20) NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(100) NOT NULL,
    phone character varying(255) NOT NULL,
    image character varying(255),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE UNIQUE INDEX users_email_unique_idx ON users(email);
CREATE UNIQUE INDEX users_name_unique_idx ON users(name);




CREATE TABLE orders (
    id INTEGER PRIMARY KEY DEFAULT nextval('orders_seq'),
    cost double precision NOT NULL,
    date date NOT NULL,
    payment_date date,
    user_id integer NOT NULL,
    status character varying(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT orders_cost_check CHECK ((cost >= (1)::double precision))
);

CREATE INDEX orders_date_idx ON orders(date);
CREATE INDEX orders_payment_date_idx ON orders(payment_date);



CREATE TABLE order_details (
    book_id integer NOT NULL,
    order_id integer NOT NULL,
    price double precision NOT NULL,
    CONSTRAINT order_details_price_check CHECK ((price >= (1)::double precision)),
    CONSTRAINT order_details_pkey PRIMARY KEY (order_id, book_id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id)
);




CREATE TABLE books_users (
    user_id integer NOT NULL,
    book_id integer NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT books_users_pkey PRIMARY KEY (user_id, book_id)
);



CREATE TABLE cart (
    book_id integer NOT NULL,
    user_id integer NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    CONSTRAINT cart_pkey PRIMARY KEY (user_id, book_id)
);















