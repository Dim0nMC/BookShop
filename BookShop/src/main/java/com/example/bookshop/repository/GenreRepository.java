package com.example.bookshop.repository;

import com.example.bookshop.model.Genre;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    @Transactional
    @Modifying
    @Query("delete from Genre a where a.id = :id")
    int delete(@Param("id") int id);
}
