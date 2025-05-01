package com.example.bookshop.repository;

import com.example.bookshop.model.Order;
import com.example.bookshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.user_id = :user")
    List<Order> findByUser_id(@Param("user") User user);

    //Optional<Order> findByIdAndUser(Integer id, User user);

}
