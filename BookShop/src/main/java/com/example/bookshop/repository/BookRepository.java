package com.example.bookshop.repository;

import com.example.bookshop.model.Book;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Transactional
    @Modifying
    @Query("delete from Book b where b.id = :id")
    int delete(@Param("id") int id);

    List<Book> findByNameContainingIgnoreCase(String namePart);
    List<Book>  findByAuthors_NameContainingIgnoreCaseOrAuthors_SurnameContainingIgnoreCase(String authorNamePart , String authorSurnamePart);
    List<Book> findByGenres_NameContainingIgnoreCase(String genresPart);
}
