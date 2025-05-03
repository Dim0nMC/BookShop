package com.example.bookshop.repository;

import com.example.bookshop.model.Author;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Transactional
    @Modifying
    @Query("delete from Author a where a.id = :id")
    @CacheEvict(value = {"authors", "authors_list"}, key = "#id")
    void delete(@Param("id") int id);


    @Override
    @Transactional
    @CachePut(value = "authors", key = "#author.id")
    @CacheEvict(value = "authors_list", allEntries = true)
    Author save(Author author);
}
