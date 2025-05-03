package com.example.bookshop.repository;

import com.example.bookshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String username);
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("delete from User u where u.id = :id")
    int delete(@Param("id") int id);

    @Query("select u from User u where trim(upper(u.name)) = trim(upper(:name))")
    Optional<User> getByNameIgnoreCase(@Param("name") String name);
}
