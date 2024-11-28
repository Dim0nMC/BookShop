package com.example.bookshop.repository;

import com.example.bookshop.model.OrderDetails;
import com.example.bookshop.model.OrderDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetailsId> {
}
